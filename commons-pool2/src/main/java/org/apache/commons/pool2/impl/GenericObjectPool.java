/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.pool2.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.SwallowedExceptionListener;
import org.apache.commons.pool2.TrackedUse;
import org.apache.commons.pool2.UsageTracking;

/**
 * A configurable {@link ObjectPool} implementation.
 * <p>
 * When coupled with the appropriate {@link PooledObjectFactory},
 * <code>GenericObjectPool</code> provides robust pooling functionality for
 * arbitrary objects.</p>
 * <p>
 * Optionally, one may configure the pool to examine and possibly evict objects
 * as they sit idle in the pool and to ensure that a minimum number of idle
 * objects are available. This is performed by an "idle object eviction" thread,
 * which runs asynchronously. Caution should be used when configuring this
 * optional feature. Eviction runs contend with client threads for access to
 * objects in the pool, so if they run too frequently performance issues may
 * result.</p>
 * <p>
 * The pool can also be configured to detect and remove "abandoned" objects,
 * i.e. objects that have been checked out of the pool but neither used nor
 * returned before the configured
 * {@link AbandonedConfig#getRemoveAbandonedTimeout() removeAbandonedTimeout}.
 * Abandoned object removal can be configured to happen when
 * <code>borrowObject</code> is invoked and the pool is close to starvation, or
 * it can be executed by the idle object evictor, or both. If pooled objects
 * implement the {@link TrackedUse} interface, their last use will be queried
 * using the <code>getLastUsed</code> method on that interface; otherwise
 * abandonment is determined by how long an object has been checked out from
 * the pool.</p>
 * <p>
 * Implementation note: To prevent possible deadlocks, care has been taken to
 * ensure that no call to a factory method will occur within a synchronization
 * block. See POOL-125 and DBCP-44 for more information.</p>
 * <p>
 * This class is intended to be thread-safe.</p>
 *
 * @see GenericKeyedObjectPool
 *
 * @param <T> Type of element pooled in this pool.
 *
 * @version $Revision: 1679560 $
 *
 * @since 2.0
 * Note:通用pool
 */
public class GenericObjectPool<T> extends BaseGenericObjectPool<T>
        implements ObjectPool<T>, GenericObjectPoolMXBean, UsageTracking<T> {

    /**
     * Create a new <code>GenericObjectPool</code> using defaults from
     * {@link GenericObjectPoolConfig}.
     *
     * @param factory The object factory to be used to create object instances
     *                used by this pool
     */
    public GenericObjectPool(PooledObjectFactory<T> factory) {
        this(factory, new GenericObjectPoolConfig());
    }

    /**
     * Create a new <code>GenericObjectPool</code> using a specific
     * configuration.
     *
     * @param factory   The object factory to be used to create object instances
     *                  used by this pool
     * @param config    The configuration to use for this pool instance. The
     *                  configuration is used by value. Subsequent changes to
     *                  the configuration object will not be reflected in the
     *                  pool.
     */
    public GenericObjectPool(PooledObjectFactory<T> factory,
            GenericObjectPoolConfig config) {

        super(config, ONAME_BASE, config.getJmxNamePrefix());

        if (factory == null) {
            jmxUnregister(); // tidy up
            throw new IllegalArgumentException("factory may not be null");
        }
        this.factory = factory;

        idleObjects = new LinkedBlockingDeque<PooledObject<T>>(config.getFairness());

        setConfig(config);

        startEvictor(getTimeBetweenEvictionRunsMillis());
    }

    /**
     * Create a new <code>GenericObjectPool</code> that tracks and destroys
     * objects that are checked out, but never returned to the pool.
     *
     * @param factory   The object factory to be used to create object instances
     *                  used by this pool
     * @param config    The base pool configuration to use for this pool instance.
     *                  The configuration is used by value. Subsequent changes to
     *                  the configuration object will not be reflected in the
     *                  pool.
     * @param abandonedConfig  Configuration for abandoned object identification
     *                         and removal.  The configuration is used by value.
     */
    public GenericObjectPool(PooledObjectFactory<T> factory,
            GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
        this(factory, config);
        setAbandonedConfig(abandonedConfig);
    }

    /**
     * Returns the cap on the number of "idle" instances in the pool. If maxIdle
     * is set too low on heavily loaded systems it is possible you will see
     * objects being destroyed and almost immediately new objects being created.
     * This is a result of the active threads momentarily returning objects
     * faster than they are requesting them them, causing the number of idle
     * objects to rise above maxIdle. The best value for maxIdle for heavily
     * loaded system will vary but the default is a good starting point.
     *
     * @return the maximum number of "idle" instances that can be held in the
     *         pool or a negative value if there is no limit
     *
     * @see #setMaxIdle
     */
    @Override
    public int getMaxIdle() {
        return maxIdle;
    }

    /**
     * Returns the cap on the number of "idle" instances in the pool. If maxIdle
     * is set too low on heavily loaded systems it is possible you will see
     * objects being destroyed and almost immediately new objects being created.
     * This is a result of the active threads momentarily returning objects
     * faster than they are requesting them them, causing the number of idle
     * objects to rise above maxIdle. The best value for maxIdle for heavily
     * loaded system will vary but the default is a good starting point.
     *
     * @param maxIdle
     *            The cap on the number of "idle" instances in the pool. Use a
     *            negative value to indicate an unlimited number of idle
     *            instances
     *
     * @see #getMaxIdle
     */
    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    /**
     * Sets the target for the minimum number of idle objects to maintain in
     * the pool. This setting only has an effect if it is positive and
     * {@link #getTimeBetweenEvictionRunsMillis()} is greater than zero. If this
     * is the case, an attempt is made to ensure that the pool has the required
     * minimum number of instances during idle object eviction runs.
     * <p>
     * If the configured value of minIdle is greater than the configured value
     * for maxIdle then the value of maxIdle will be used instead.
     *
     * @param minIdle
     *            The minimum number of objects.
     *
     * @see #getMinIdle()
     * @see #getMaxIdle()
     * @see #getTimeBetweenEvictionRunsMillis()
     */
    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    /**
     * Returns the target for the minimum number of idle objects to maintain in
     * the pool. This setting only has an effect if it is positive and
     * {@link #getTimeBetweenEvictionRunsMillis()} is greater than zero. If this
     * is the case, an attempt is made to ensure that the pool has the required
     * minimum number of instances during idle object eviction runs.
     * <p>
     * If the configured value of minIdle is greater than the configured value
     * for maxIdle then the value of maxIdle will be used instead.
     *
     * @return The minimum number of objects.
     *
     * @see #setMinIdle(int)
     * @see #setMaxIdle(int)
     * @see #setTimeBetweenEvictionRunsMillis(long)
     */
    @Override
    public int getMinIdle() {
        int maxIdleSave = getMaxIdle();//最大的空闲数量
        if (this.minIdle > maxIdleSave) {//如果最小的大于最大的，则返回最大的
            return maxIdleSave;
        } else {
            return minIdle;//返回最小的
        }
    }

    /**
     * Whether or not abandoned object removal is configured for this pool.
     *
     * @return true if this pool is configured to detect and remove
     * abandoned objects
     */
    @Override
    public boolean isAbandonedConfig() {
        return abandonedConfig != null;
    }

    /**
     * Will this pool identify and log any abandoned objects?
     *
     * @return {@code true} if abandoned object removal is configured for this
     *         pool and removal events are to be logged otherwise {@code false}
     *
     * @see AbandonedConfig#getLogAbandoned()
     */
    @Override
    public boolean getLogAbandoned() {
        AbandonedConfig ac = this.abandonedConfig;
        return ac != null && ac.getLogAbandoned();
    }

    /**
     * Will a check be made for abandoned objects when an object is borrowed
     * from this pool?
     *
     * @return {@code true} if abandoned object removal is configured to be
     *         activated by borrowObject otherwise {@code false}
     *
     * @see AbandonedConfig#getRemoveAbandonedOnBorrow()
     */
    @Override
    public boolean getRemoveAbandonedOnBorrow() {
        AbandonedConfig ac = this.abandonedConfig;
        return ac != null && ac.getRemoveAbandonedOnBorrow();
    }

    /**
     * Will a check be made for abandoned objects when the evictor runs?
     *
     * @return {@code true} if abandoned object removal is configured to be
     *         activated when the evictor runs otherwise {@code false}
     *
     * @see AbandonedConfig#getRemoveAbandonedOnMaintenance()
     */
    @Override
    public boolean getRemoveAbandonedOnMaintenance() {
        AbandonedConfig ac = this.abandonedConfig;
        return ac != null && ac.getRemoveAbandonedOnMaintenance();
    }

    /**
     * Obtain the timeout before which an object will be considered to be
     * abandoned by this pool.
     *
     * @return The abandoned object timeout in seconds if abandoned object
     *         removal is configured for this pool; Integer.MAX_VALUE otherwise.
     *
     * @see AbandonedConfig#getRemoveAbandonedTimeout()
     */
    @Override
    public int getRemoveAbandonedTimeout() {
        AbandonedConfig ac = this.abandonedConfig;
        return ac != null ? ac.getRemoveAbandonedTimeout() : Integer.MAX_VALUE;
    }


    /**
     * Sets the base pool configuration.
     *
     * @param conf the new configuration to use. This is used by value.
     *
     * @see GenericObjectPoolConfig
     */
    public void setConfig(GenericObjectPoolConfig conf) {
        setLifo(conf.getLifo());
        setMaxIdle(conf.getMaxIdle());
        setMinIdle(conf.getMinIdle());
        setMaxTotal(conf.getMaxTotal());
        setMaxWaitMillis(conf.getMaxWaitMillis());
        setBlockWhenExhausted(conf.getBlockWhenExhausted());
        setTestOnCreate(conf.getTestOnCreate());
        setTestOnBorrow(conf.getTestOnBorrow());
        setTestOnReturn(conf.getTestOnReturn());
        setTestWhileIdle(conf.getTestWhileIdle());
        setNumTestsPerEvictionRun(conf.getNumTestsPerEvictionRun());
        setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
        setTimeBetweenEvictionRunsMillis(
                conf.getTimeBetweenEvictionRunsMillis());
        setSoftMinEvictableIdleTimeMillis(
                conf.getSoftMinEvictableIdleTimeMillis());
        setEvictionPolicyClassName(conf.getEvictionPolicyClassName());
    }

    /**
     * Sets the abandoned object removal configuration.
     *
     * @param abandonedConfig the new configuration to use. This is used by value.
     *
     * @see AbandonedConfig
     */
    public void setAbandonedConfig(AbandonedConfig abandonedConfig) throws IllegalArgumentException {
        if (abandonedConfig == null) {
            this.abandonedConfig = null;
        } else {
            this.abandonedConfig = new AbandonedConfig();
            this.abandonedConfig.setLogAbandoned(abandonedConfig.getLogAbandoned());
            this.abandonedConfig.setLogWriter(abandonedConfig.getLogWriter());
            this.abandonedConfig.setRemoveAbandonedOnBorrow(abandonedConfig.getRemoveAbandonedOnBorrow());
            this.abandonedConfig.setRemoveAbandonedOnMaintenance(abandonedConfig.getRemoveAbandonedOnMaintenance());
            this.abandonedConfig.setRemoveAbandonedTimeout(abandonedConfig.getRemoveAbandonedTimeout());
            this.abandonedConfig.setUseUsageTracking(abandonedConfig.getUseUsageTracking());
        }
    }

    /**
     * Obtain a reference to the factory used to create, destroy and validate
     * the objects used by this pool.
     *
     * @return the factory
     */
    public PooledObjectFactory<T> getFactory() {
        return factory;
    }

    /**
     * Equivalent to <code>{@link #borrowObject(long)
     * borrowObject}({@link #getMaxWaitMillis()})</code>.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public T borrowObject() throws Exception {
        return borrowObject(getMaxWaitMillis());
    }

    /**
     * Borrow an object from the pool using the specific waiting time which only
     * applies if {@link #getBlockWhenExhausted()} is true.
     * <p>
     * If there is one or more idle instance available in the pool, then an
     * idle instance will be selected based on the value of {@link #getLifo()},
     * activated and returned. If activation fails, or {@link #getTestOnBorrow()
     * testOnBorrow} is set to <code>true</code> and validation fails, the
     * instance is destroyed and the next available instance is examined. This
     * continues until either a valid instance is returned or there are no more
     * idle instances available.
     * <p>
     * If there are no idle instances available in the pool, behavior depends on
     * the {@link #getMaxTotal() maxTotal}, (if applicable)
     * {@link #getBlockWhenExhausted()} and the value passed in to the
     * <code>borrowMaxWaitMillis</code> parameter. If the number of instances
     * checked out from the pool is less than <code>maxTotal,</code> a new
     * instance is created, activated and (if applicable) validated and returned
     * to the caller. If validation fails, a <code>NoSuchElementException</code>
     * is thrown.
     * <p>
     * If the pool is exhausted (no available idle instances and no capacity to
     * create new ones), this method will either block (if
     * {@link #getBlockWhenExhausted()} is true) or throw a
     * <code>NoSuchElementException</code> (if
     * {@link #getBlockWhenExhausted()} is false). The length of time that this
     * method will block when {@link #getBlockWhenExhausted()} is true is
     * determined by the value passed in to the <code>borrowMaxWaitMillis</code>
     * parameter.
     * <p>
     * When the pool is exhausted, multiple calling threads may be
     * simultaneously blocked waiting for instances to become available. A
     * "fairness" algorithm has been implemented to ensure that threads receive
     * available instances in request arrival order.
     *
     * @param borrowMaxWaitMillis The time to wait in milliseconds for an object
     *                            to become available
     *
     * @return object instance from the pool
     *
     * @throws NoSuchElementException if an instance cannot be returned
     *
     * @throws Exception if an object instance cannot be returned due to an
     *                   error
     */
    public T borrowObject(long borrowMaxWaitMillis) throws Exception {
        assertOpen();//判断是否打开

        AbandonedConfig ac = this.abandonedConfig;
        if (ac != null && ac.getRemoveAbandonedOnBorrow() &&
                (getNumIdle() < 2) &&
                (getNumActive() > getMaxTotal() - 3) ) {
            removeAbandoned(ac);
        }

        PooledObject<T> p = null;

        // Get local copy of current config so it is consistent for entire
        // method execution
        boolean blockWhenExhausted = getBlockWhenExhausted();//是否阻塞影响创建失败后的处理 阻塞，则创建失败后仍然可以继续等待

        boolean create;
        long waitTime = System.currentTimeMillis();

        while (p == null) {
            create = false;
            if (blockWhenExhausted) {//当耗尽时候阻塞
                p = idleObjects.pollFirst();//空闲队列取出第一个，从队列里删除，如果为空则返回null
                if (p == null) {//为null
                    p = create();//创建一个新的 当达到做大限度时候，会创建失败
                    if (p != null) {//创建成功
                        create = true;
                    }
                }
                if (p == null) {//创建失败
                    if (borrowMaxWaitMillis < 0) {//BaseObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS
                        p = idleObjects.takeFirst();//一直阻塞知道获取到一个对象
                    } else {
                        p = idleObjects.pollFirst(borrowMaxWaitMillis,
                                TimeUnit.MILLISECONDS);//一直阻塞知道获取到一个对象，但是有时间限制
                    }
                }
                if (p == null) {
                    throw new NoSuchElementException(
                            "Timeout waiting for idle object");
                }
                if (!p.allocate()) {
                    p = null;
                }
            } else {//不阻塞
                p = idleObjects.pollFirst();//获取第一个
                if (p == null) {
                    p = create();
                    if (p != null) {
                        create = true;
                    }
                }
                if (p == null) {
                    throw new NoSuchElementException("Pool exhausted");
                }
                if (!p.allocate()) {//如果分配失败 则进入下一次循环
                    p = null;
                }
            }

            if (p != null) {//获取到对象了
                try {
                    factory.activateObject(p);//激活
                } catch (Exception e) {
                    try {
                        destroy(p);//销毁
                    } catch (Exception e1) {
                        // Ignore - activation failure is more important
                    }
                    p = null;
                    if (create) {
                        NoSuchElementException nsee = new NoSuchElementException(
                                "Unable to activate object");
                        nsee.initCause(e);
                        throw nsee;
                    }
                }
                if (p != null && (getTestOnBorrow() || create && getTestOnCreate())) {//是否校验 borrow时校验或者创建并且创建时校验
                    boolean validate = false;
                    Throwable validationThrowable = null;
                    try {
                        validate = factory.validateObject(p);
                    } catch (Throwable t) {
                        PoolUtils.checkRethrow(t);
                        validationThrowable = t;
                    }
                    if (!validate) {//校验不通过
                        try {
                            destroy(p);//销毁
                            destroyedByBorrowValidationCount.incrementAndGet();
                        } catch (Exception e) {
                            // Ignore - validation failure is more important
                        }
                        p = null;
                        if (create) {//创建成功但是校验失败，抛出异常
                            NoSuchElementException nsee = new NoSuchElementException(
                                    "Unable to validate object");
                            nsee.initCause(validationThrowable);
                            throw nsee;
                        }
                    }
                }
            }
        }

        updateStatsBorrow(p, System.currentTimeMillis() - waitTime);//更新统计信息

        return p.getObject();
    }

    /**
     * {@inheritDoc}
     * <p>
     * If {@link #getMaxIdle() maxIdle} is set to a positive value and the
     * number of idle instances has reached this value, the returning instance
     * is destroyed.
     * <p>
     * If {@link #getTestOnReturn() testOnReturn} == true, the returning
     * instance is validated before being returned to the idle instance pool. In
     * this case, if validation fails, the instance is destroyed.
     * <p>
     * Exceptions encountered destroying objects for any reason are swallowed
     * but notified via a {@link SwallowedExceptionListener}.
     */
    @Override
    public void returnObject(T obj) {
        PooledObject<T> p = allObjects.get(new IdentityWrapper<T>(obj));
        
        if (p == null) {
            if (!isAbandonedConfig()) {
                throw new IllegalStateException(
                        "Returned object not currently part of this pool");
            } else {
                return; // Object was abandoned and removed
            }
        }

        synchronized(p) {
            final PooledObjectState state = p.getState();
            if (state != PooledObjectState.ALLOCATED) {
                throw new IllegalStateException(
                        "Object has already been returned to this pool or is invalid");
            } else {
                p.markReturning(); // Keep from being marked abandoned
            }
        }

        long activeTime = p.getActiveTimeMillis();

        if (getTestOnReturn()) {
            if (!factory.validateObject(p)) {
                try {
                    destroy(p);
                } catch (Exception e) {
                    swallowException(e);
                }
                try {
                    ensureIdle(1, false);
                } catch (Exception e) {
                    swallowException(e);
                }
                updateStatsReturn(activeTime);
                return;
            }
        }

        try {
            factory.passivateObject(p);
        } catch (Exception e1) {
            swallowException(e1);
            try {
                destroy(p);
            } catch (Exception e) {
                swallowException(e);
            }
            try {
                ensureIdle(1, false);
            } catch (Exception e) {
                swallowException(e);
            }
            updateStatsReturn(activeTime);
            return;
        }

        if (!p.deallocate()) {
            throw new IllegalStateException(
                    "Object has already been returned to this pool or is invalid");
        }

        int maxIdleSave = getMaxIdle();
        if (isClosed() || maxIdleSave > -1 && maxIdleSave <= idleObjects.size()) {
            try {
                destroy(p);
            } catch (Exception e) {
                swallowException(e);
            }
        } else {
            if (getLifo()) {
                idleObjects.addFirst(p);
            } else {
                idleObjects.addLast(p);
            }
            if (isClosed()) {
                // Pool closed while object was being added to idle objects.
                // Make sure the returned object is destroyed rather than left
                // in the idle object pool (which would effectively be a leak)
                clear();
            }
        }
        updateStatsReturn(activeTime);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Activation of this method decrements the active count and attempts to
     * destroy the instance.
     *
     * @throws Exception             if an exception occurs destroying the
     *                               object
     * @throws IllegalStateException if obj does not belong to this pool
     */
    @Override
    public void invalidateObject(T obj) throws Exception {
        PooledObject<T> p = allObjects.get(new IdentityWrapper<T>(obj));
        if (p == null) {
            if (isAbandonedConfig()) {
                return;
            } else {
                throw new IllegalStateException(
                        "Invalidated object not currently part of this pool");
            }
        }
        synchronized (p) {
            if (p.getState() != PooledObjectState.INVALID) {
                destroy(p);
            }
        }
        ensureIdle(1, false);
    }

    /**
     * Clears any objects sitting idle in the pool by removing them from the
     * idle instance pool and then invoking the configured
     * {@link PooledObjectFactory#destroyObject(PooledObject)} method on each
     * idle instance.
     * <p>
     * Implementation notes:
     * <ul>
     * <li>This method does not destroy or effect in any way instances that are
     * checked out of the pool when it is invoked.</li>
     * <li>Invoking this method does not prevent objects being returned to the
     * idle instance pool, even during its execution. Additional instances may
     * be returned while removed items are being destroyed.</li>
     * <li>Exceptions encountered destroying idle instances are swallowed
     * but notified via a {@link SwallowedExceptionListener}.</li>
     * </ul>
     */
    @Override
    public void clear() {//清理对象池
        PooledObject<T> p = idleObjects.poll();//清理所有空闲的对象

        while (p != null) {
            try {
                destroy(p);
            } catch (Exception e) {
                swallowException(e);
            }
            p = idleObjects.poll();
        }
    }
    //active为所有对象-空闲对象
    @Override
    public int getNumActive() {
        return allObjects.size() - idleObjects.size();
    }

    @Override
    public int getNumIdle() {
        return idleObjects.size();
    }

    /**
     * Closes the pool. Once the pool is closed, {@link #borrowObject()} will
     * fail with IllegalStateException, but {@link #returnObject(Object)} and
     * {@link #invalidateObject(Object)} will continue to work, with returned
     * objects destroyed on return.
     * <p>
     * Destroys idle instances in the pool by invoking {@link #clear()}.
     */
    @Override
    public void close() {
        if (isClosed()) {
            return;
        }

        synchronized (closeLock) {
            if (isClosed()) {//已经关闭了
                return;
            }

            // Stop the evictor before the pool is closed since evict() calls
            // assertOpen()
            startEvictor(-1L);//停止检测线程

            closed = true;
            // This clear removes any idle objects
            clear();//清除空闲对象

            jmxUnregister();

            // Release any threads that were waiting for an object
            //释放所有在等待取对象的线程
            idleObjects.interuptTakeWaiters();
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Successive activations of this method examine objects in sequence,
     * cycling through objects in oldest-to-youngest order.
     * 按照空闲时间的倒序来检测
     * 空闲时间越大，越靠前
     * 对空闲的队列进行检测
     */
    @Override
    public void evict() throws Exception {
        assertOpen();

        if (idleObjects.size() > 0) {//有空闲的对象

            PooledObject<T> underTest = null;
            EvictionPolicy<T> evictionPolicy = getEvictionPolicy();//驱逐策略

            synchronized (evictionLock) {
                EvictionConfig evictionConfig = new EvictionConfig(
                        getMinEvictableIdleTimeMillis(),
                        getSoftMinEvictableIdleTimeMillis(),
                        getMinIdle());//初始化驱逐配置：最小空闲时间，软引用最小空闲时间，最小空闲数量

                boolean testWhileIdle = getTestWhileIdle();//空闲是是否检测

                for (int i = 0, m = getNumTests(); i < m; i++) {
                    if (evictionIterator == null || !evictionIterator.hasNext()) {
                        evictionIterator = new EvictionIterator(idleObjects);
                    }
                    if (!evictionIterator.hasNext()) {
                        // Pool exhausted, nothing to do here
                        return;
                    }

                    try {
                        underTest = evictionIterator.next();
                    } catch (NoSuchElementException nsee) {
                        // Object was borrowed in another thread
                        // Don't count this as an eviction test so reduce i;
                        i--;
                        evictionIterator = null;
                        continue;
                    }

                    if (!underTest.startEvictionTest()) {//只有空闲状态才去检测，这里有可能其他线程已经borrow了
                        // Object was borrowed in another thread
                        // Don't count this as an eviction test so reduce i;
                        i--;//不算数
                        continue;
                    }

                    // User provided eviction policy could throw all sorts of
                    // crazy exceptions. Protect against such an exception
                    // killing the eviction thread.
                    boolean evict;
                    try {
                        evict = evictionPolicy.evict(evictionConfig, underTest,
                                idleObjects.size());//是否驱逐
                    } catch (Throwable t) {
                        // Slightly convoluted as SwallowedExceptionListener
                        // uses Exception rather than Throwable
                        PoolUtils.checkRethrow(t);
                        swallowException(new Exception(t));
                        // Don't evict on error conditions
                        evict = false;
                    }

                    if (evict) {//要驱逐
                        destroy(underTest);//销毁
                        destroyedByEvictorCount.incrementAndGet();//统计
                    } else {//不驱逐
                        if (testWhileIdle) {//空闲时要检测 检测有效性
                            boolean active = false;
                            try {
                                factory.activateObject(underTest);//激活
                                active = true;
                            } catch (Exception e) {
                                destroy(underTest);
                                destroyedByEvictorCount.incrementAndGet();
                            }
                            if (active) {//激活了
                                if (!factory.validateObject(underTest)) {//检测
                                    destroy(underTest);
                                    destroyedByEvictorCount.incrementAndGet();
                                } else {//没有激活
                                    try {
                                        factory.passivateObject(underTest);//
                                    } catch (Exception e) {
                                        destroy(underTest);
                                        destroyedByEvictorCount.incrementAndGet();
                                    }
                                }
                            }
                        }
                        if (!underTest.endEvictionTest(idleObjects)) {//没有停止检测 啥都不做
                            // TODO - May need to add code here once additional
                            // states are used
                        }
                    }
                }
            }
        }
        AbandonedConfig ac = this.abandonedConfig;//是否清理
        if (ac != null && ac.getRemoveAbandonedOnMaintenance()) {
            removeAbandoned(ac);
        }
    }
    
    /**
     * Tries to ensure that {@link #getMinIdle()} idle instances are available
     * in the pool.
     * 保证最小的空闲数量
     *
     * @throws Exception If the associated factory throws an exception
     * @since 2.4
     */
    public void preparePool() throws Exception {
        if (getMinIdle() < 1) {
            return;
        }
        ensureMinIdle();
    }

    /**
     * Attempts to create a new wrapped pooled object.
     * 尝试去创建一个新的对象
     * <p>
     * If there are {@link #getMaxTotal()} objects already in circulation
     * or in process of being created, this method returns null.
     *
     * @return The new wrapped pooled object
     *
     * @throws Exception if the object factory's {@code makeObject} fails
     */
    private PooledObject<T> create() throws Exception {
        int localMaxTotal = getMaxTotal();//最大限度
        long newCreateCount = createCount.incrementAndGet();//新的创建的总数
        if (localMaxTotal > -1 && newCreateCount > localMaxTotal ||
                newCreateCount > Integer.MAX_VALUE) {//如果超过最大限度或者超过int最大值，则返回null，不予创建
            createCount.decrementAndGet();//减去一个
            return null;
        }

        final PooledObject<T> p;
        try {
            p = factory.makeObject();//工厂方法里获取一个新对象
        } catch (Exception e) {
            createCount.decrementAndGet();//创建失败，减去一个
            throw e;
        }
        AbandonedConfig ac = this.abandonedConfig;
        if (ac != null && ac.getLogAbandoned()) {
            p.setLogAbandoned(true);
        }

        createdCount.incrementAndGet();
        allObjects.put(new IdentityWrapper<T>(p.getObject()), p);//放到所有对象队列中
        return p;
    }

    /**
     * Destroys a wrapped pooled object.
     * 从池中移除 销毁
     *
     * @param toDestory The wrapped pooled object to destroy
     *
     * @throws Exception If the factory fails to destroy the pooled object
     *                   cleanly
     */
    private void destroy(PooledObject<T> toDestory) throws Exception {
        toDestory.invalidate();
        idleObjects.remove(toDestory);
        allObjects.remove(new IdentityWrapper<T>(toDestory.getObject()));
        try {
            factory.destroyObject(toDestory);
        } finally {
            destroyedCount.incrementAndGet();
            createCount.decrementAndGet();
        }
    }

    @Override
    void ensureMinIdle() throws Exception {
        ensureIdle(getMinIdle(), true);
    }

    /**
     * Tries to ensure that {@code idleCount} idle instances exist in the pool.
     * 试着保证有空闲对象在对象池里
     * <p>
     * Creates and adds idle instances until either {@link #getNumIdle()} reaches {@code idleCount}
     * or the total number of objects (idle, checked out, or being created) reaches
     * {@link #getMaxTotal()}. If {@code always} is false, no instances are created unless
     * there are threads waiting to check out instances from the pool.
     *
     * @param idleCount the number of idle instances desired
     * @param always true means create instances even if the pool has no threads waiting
     * @throws Exception if the factory's makeObject throws
     */
    private void ensureIdle(int idleCount, boolean always) throws Exception {
        if (idleCount < 1 || isClosed() || (!always && !idleObjects.hasTakeWaiters())) {
            //空闲数量小于1 或者关闭 或者 不是always并且空闲队列没有人等待
            return;
        }

        while (idleObjects.size() < idleCount) {//当当前的数量小于要确保的数量的时候，则创建对象
            PooledObject<T> p = create();
            if (p == null) {
                // Can't create objects, no reason to think another call to
                // create will work. Give up.
                break;
            }
            if (getLifo()) {
                idleObjects.addFirst(p);
            } else {
                idleObjects.addLast(p);
            }
        }
        if (isClosed()) {
            // Pool closed while object was being added to idle objects.
            // Make sure the returned object is destroyed rather than left
            // in the idle object pool (which would effectively be a leak)
            clear();
        }
    }

    /**
     * Create an object, and place it into the pool. addObject() is useful for
     * "pre-loading" a pool with idle objects.
     * <p>
     * If there is no capacity available to add to the pool, this is a no-op
     * (no exception, no impact to the pool). </p>
     * 添加新创建的对象
     * 用于预创建的时候
     */
    @Override
    public void addObject() throws Exception {
        assertOpen();
        if (factory == null) {
            throw new IllegalStateException(
                    "Cannot add objects without a factory.");
        }
        PooledObject<T> p = create();
        addIdleObject(p);
    }

    /**
     * Add the provided wrapped pooled object to the set of idle objects for
     * this pool. The object must already be part of the pool.  If {@code p}
     * is null, this is a no-op (no exception, but no impact on the pool).
     *
     * @param p The object to make idle
     *
     * @throws Exception If the factory fails to passivate the object
     */
    private void addIdleObject(PooledObject<T> p) throws Exception {
        if (p != null) {
            factory.passivateObject(p);
            if (getLifo()) {//栈
                idleObjects.addFirst(p);
            } else {//队列
                idleObjects.addLast(p);
            }
        }
    }

    /**
     * Calculate the number of objects to test in a run of the idle object
     * evictor.
     * 计算这次检测多少个
     * @return The number of objects to test for validity
     */
    private int getNumTests() {
        int numTestsPerEvictionRun = getNumTestsPerEvictionRun();
        if (numTestsPerEvictionRun >= 0) {//设置了值，则返回当前值和空闲队列大小的最小值
            return Math.min(numTestsPerEvictionRun, idleObjects.size());
        } else {//小于0，则取空闲数量/绝对值（数字）然后取地板
            return (int) (Math.ceil(idleObjects.size() /
                    Math.abs((double) numTestsPerEvictionRun)));
        }
    }

    /**
     * Recover abandoned objects which have been checked out but
     * not used since longer than the removeAbandonedTimeout.
     *
     * @param ac The configuration to use to identify abandoned objects
     */
    private void removeAbandoned(AbandonedConfig ac) {
        // Generate a list of abandoned objects to remove
        final long now = System.currentTimeMillis();
        final long timeout =
                now - (ac.getRemoveAbandonedTimeout() * 1000L);
        ArrayList<PooledObject<T>> remove = new ArrayList<PooledObject<T>>();
        Iterator<PooledObject<T>> it = allObjects.values().iterator();
        while (it.hasNext()) {
            PooledObject<T> pooledObject = it.next();
            synchronized (pooledObject) {
                if (pooledObject.getState() == PooledObjectState.ALLOCATED &&
                        pooledObject.getLastUsedTime() <= timeout) {
                    pooledObject.markAbandoned();
                    remove.add(pooledObject);
                }
            }
        }

        // Now remove the abandoned objects
        Iterator<PooledObject<T>> itr = remove.iterator();
        while (itr.hasNext()) {
            PooledObject<T> pooledObject = itr.next();
            if (ac.getLogAbandoned()) {
                pooledObject.printStackTrace(ac.getLogWriter());
            }
            try {
                invalidateObject(pooledObject.getObject());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //--- Usage tracking support -----------------------------------------------

    @Override
    public void use(T pooledObject) {
        AbandonedConfig ac = this.abandonedConfig;
        if (ac != null && ac.getUseUsageTracking()) {
            PooledObject<T> wrapper = allObjects.get(new IdentityWrapper<T>(pooledObject));
            wrapper.use();
        }
    }


    //--- JMX support ----------------------------------------------------------

    private volatile String factoryType = null;

    /**
     * Return an estimate of the number of threads currently blocked waiting for
     * an object from the pool. This is intended for monitoring only, not for
     * synchronization control.
     *
     * @return The estimate of the number of threads currently blocked waiting
     *         for an object from the pool
     */
    @Override
    public int getNumWaiters() {
        if (getBlockWhenExhausted()) {
            return idleObjects.getTakeQueueLength();
        } else {
            return 0;
        }
    }

    /**
     * Return the type - including the specific type rather than the generic -
     * of the factory.
     *
     * @return A string representation of the factory type
     */
    @Override
    public String getFactoryType() {
        // Not thread safe. Accept that there may be multiple evaluations.
        if (factoryType == null) {
            StringBuilder result = new StringBuilder();
            result.append(factory.getClass().getName());
            result.append('<');
            Class<?> pooledObjectType =
                    PoolImplUtils.getFactoryType(factory.getClass());
            result.append(pooledObjectType.getName());
            result.append('>');
            factoryType = result.toString();
        }
        return factoryType;
    }

    /**
     * Provides information on all the objects in the pool, both idle (waiting
     * to be borrowed) and active (currently borrowed).
     * <p>
     * Note: This is named listAllObjects so it is presented as an operation via
     * JMX. That means it won't be invoked unless the explicitly requested
     * whereas all attributes will be automatically requested when viewing the
     * attributes for an object in a tool like JConsole.
     *
     * @return Information grouped on all the objects in the pool
     */
    @Override
    public Set<DefaultPooledObjectInfo> listAllObjects() {
        Set<DefaultPooledObjectInfo> result =
                new HashSet<DefaultPooledObjectInfo>(allObjects.size());
        for (PooledObject<T> p : allObjects.values()) {
            result.add(new DefaultPooledObjectInfo(p));
        }
        return result;
    }

    // --- configuration attributes --------------------------------------------

    private volatile int maxIdle = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;
    private volatile int minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;
    private final PooledObjectFactory<T> factory;


    // --- internal attributes -------------------------------------------------

    /*
     * All of the objects currently associated with this pool in any state. It
     * excludes objects that have been destroyed. The size of
     * {@link #allObjects} will always be less than or equal to {@link
     * #_maxActive}. Map keys are pooled objects, values are the PooledObject
     * wrappers used internally by the pool.
     * 池中的所有对象，包括各种状态的，不包含已经被销毁的，大小小于等于maxActive
     */
    private final Map<IdentityWrapper<T>, PooledObject<T>> allObjects =
        new ConcurrentHashMap<IdentityWrapper<T>, PooledObject<T>>();
    /*
     * The combined count of the currently created objects and those in the
     * process of being created. Under load, it may exceed {@link #_maxActive}
     * if multiple threads try and create a new object at the same time but
     * {@link #create()} will ensure that there are never more than
     * {@link #_maxActive} objects created at any one time.
     */
    private final AtomicLong createCount = new AtomicLong(0);
    //这个队列存储空闲对象，
    private final LinkedBlockingDeque<PooledObject<T>> idleObjects;

    // JMX specific attributes
    private static final String ONAME_BASE =
        "org.apache.commons.pool2:type=GenericObjectPool,name=";

    // Additional configuration properties for abandoned object tracking
    private volatile AbandonedConfig abandonedConfig = null;
    
}
