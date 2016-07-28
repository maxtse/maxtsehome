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
package org.apache.commons.pool2;

import java.io.PrintWriter;
import java.util.Deque;

/**
 * Defines the wrapper that is used to track the additional information, such as
 * state, for the pooled objects.
 * <p>
 * Implementations of this class are required to be thread-safe.
 *
 * @param <T> the type of object in the pool
 *
 * @version $Revision: $
 *
 * @since 2.0
 * Note
 * 对池中对象的封装，添加了一些附加信息
 * 获取池中对象的一些信息：
 * 创建时间（createTime)
 * 活跃时间（activeTime)
 * 空闲时间
 * 最后一次借用时间
 * 最后一次返回时间
 *
 *
 */
public interface PooledObject<T> extends Comparable<PooledObject<T>> {

    /**
     * Obtain the underlying object that is wrapped by this instance of
     * 获取原始对象
     * {@link PooledObject}.
     *
     * @return The wrapped object
     */
    T getObject();

    /**
     * Obtain the time (using the same basis as
     * {@link System#currentTimeMillis()}) that this object was created.
     *
     * @return The creation time for the wrapped object
     * 获取创建时间
     */
    long getCreateTime();

    /**
     * Obtain the time in milliseconds that this object last spent in the the
     * active state (it may still be active in which case subsequent calls will
     * return an increased value).
     *
     * @return The time in milliseconds last spent in the active state
     * 处于活跃状态的时间
     */
    long getActiveTimeMillis();

    /**
     * Obtain the time in milliseconds that this object last spend in the the
     * idle state (it may still be idle in which case subsequent calls will
     * return an increased value).
     * 处于空闲状态的时间
     * @return The time in milliseconds last spent in the idle state
     */
    long getIdleTimeMillis();

    /**
     * Obtain the time the wrapped object was last borrowed.
     * 对象最后一次被借出的时间
     * @return The time the object was last borrowed
     */
    long getLastBorrowTime();

    /**
     * Obtain the time the wrapped object was last returned.
     * 最后一次返回的时间
     * @return The time the object was last returned
     */
    long getLastReturnTime();

    /**
     * Return an estimate of the last time this object was used.  If the class
     * of the pooled object implements {@link TrackedUse}, what is returned is
     * the maximum of {@link TrackedUse#getLastUsed()} and
     * {@link #getLastBorrowTime()}; otherwise this method gives the same
     * value as {@link #getLastBorrowTime()}.
     *
     * @return the last time this object was used
     */
    long getLastUsedTime();

    /**
     * Orders instances based on idle time - i.e. the length of time since the
     * instance was returned to the pool. Used by the GKOP idle object evictor.
     *<p>
     * Note: This class has a natural ordering that is inconsistent with
     *       equals if distinct objects have the same identity hash code.
     * <p>
     * {@inheritDoc}
     */
    @Override
    int compareTo(PooledObject<T> other);

    @Override
    boolean equals(Object obj);

    @Override
    int hashCode();

    /**
     * Provides a String form of the wrapper for debug purposes. The format is
     * not fixed and may change at any time.
     * <p>
     * {@inheritDoc}
     */
    @Override
    String toString();

    /**
     * Attempt to place the pooled object in the
     * {@link PooledObjectState#EVICTION} state.
     * 对于idle的状态，把状态设置为EVICTION
     *
     * @return <code>true</code> if the object was placed in the
     *         {@link PooledObjectState#EVICTION} state otherwise
     *         <code>false</code>
     *         设置对象为EVICTION状态
     *         判断是否可以进行驱逐检测
     */
    boolean startEvictionTest();

    /**
     * Called to inform the object that the eviction test has ended.
     * 结束驱逐检测
     * @param idleQueue The queue of idle objects to which the object should be
     *                  returned
     * 结束驱逐检测，更改状态为IDLE
     *
     * @return  Currently not used
     */
    boolean endEvictionTest(Deque<PooledObject<T>> idleQueue);

    /**
     * Allocates the object.
     * 分配对象
     * 更改状态为ALLOCATED
     * 只有IDLE对象可以流转为ALLOCATED
     *
     * @return {@code true} if the original state was {@link PooledObjectState#IDLE IDLE}
     */
    boolean allocate();

    /**
     * Deallocates the object and sets it {@link PooledObjectState#IDLE IDLE}
     * if it is currently {@link PooledObjectState#ALLOCATED ALLOCATED}.
     * 取消分配
     * 修改状态为RETURNING
     *
     * @return {@code true} if the state was {@link PooledObjectState#ALLOCATED ALLOCATED}
     */
    boolean deallocate();

    /**
     * Sets the state to {@link PooledObjectState#INVALID INVALID}
     * 使对象失效
     */
    void invalidate();

    /**
     * Is abandoned object tracking being used? If this is true the
     * implementation will need to record the stack trace of the last caller to
     * borrow this object.
     *
     * @param   logAbandoned    The new configuration setting for abandoned
     *                          object tracking
     */
    void setLogAbandoned(boolean logAbandoned);

    /**
     * Record the current stack trace as the last time the object was used.
     *
     */
    void use();

    /**
     * Prints the stack trace of the code that borrowed this pooled object and
     * the stack trace of the last code to use this object (if available) to
     * the supplied writer.
     *
     * @param   writer  ThePool destination for the debug output
     */
    void printStackTrace(PrintWriter writer);

    /**
     * Returns the state of this object.
     * 获取状态
     * @return state
     */
    PooledObjectState getState();

    /**
     * Marks the pooled object as abandoned.
     * 标记为要废弃
     */
    void markAbandoned();

    /**
     * Marks the object as returning to the pool.
     * 标记为要返回
     */
    void markReturning();

    // TODO: Uncomment this for version 3 (can't add it to 2.x as it will break
    //       API compatibility)
    ///**
    // * Get the number of times this object has been borrowed.
    // */
    //long getBorrowedCount();
}
