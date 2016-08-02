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

/**
 * Provides the possible states that a {@link PooledObject} may be in.
 *
 * @version $Revision: $
 *
 * @since 2.0
 */
public enum PooledObjectState {
    /**
     * In the queue, not in use.
     * 空闲 idleObjects
     */
    IDLE,

    /**
     * In use.
     * 分配 not in the queue
     * poolFirst
     */
    ALLOCATED,

    /**
     * In the queue, currently being tested for possible eviction.
     * 在队列里，正在测试，可能被驱逐
     * startEvictionTest
     */
    EVICTION,

    /**
     * Not in the queue, currently being tested for possible eviction. An
     * attempt to borrow the object was made while being tested which removed it
     * from the queue. It should be returned to the head of the queue once
     * eviction testing completes.
     * TODO: Consider allocating object and ignoring the result of the eviction
     *       test.
     * 不在空闲队列里，当前正在测试，但是又打算borrow，因此校验完毕后应该放到队列头部
     *
     */
    EVICTION_RETURN_TO_HEAD,

    /**
     * In the queue, currently being validated.
     * 正在被校验是否驱逐
     * poolObject.startEvictionTest()来把状态从IDLE修改为VALIDATION
     */
    VALIDATION,

    /**
     * Not in queue, currently being validated. The object was borrowed while
     * being validated and since testOnBorrow was configured, it was removed
     * from the queue and pre-allocated. It should be allocated once validation
     * completes.
     */
    VALIDATION_PREALLOCATED,

    /**
     * Not in queue, currently being validated. An attempt to borrow the object
     * was made while previously being tested for eviction which removed it from
     * the queue. It should be returned to the head of the queue once validation
     * completes.
     * 不在队列里，正在被校验，
     */
    VALIDATION_RETURN_TO_HEAD,

    /**
     * Failed maintenance (e.g. eviction test or validation) and will be / has
     * been destroyed
     * 失效
     * poolObject.invalidate()状态设置为INVALID
     * ObjectPool.destory()
     */
    INVALID,

    /**
     * Deemed abandoned, to be invalidated.
     * 被认为放弃，将要被标记为无效状态
     * poolObject.markAbandoned() 状态设置为ABANDONED
     * 标记为要被废弃
     */
    ABANDONED,

    /**
     * Returning to the pool.
     * 收回对象
     * 标记为要回收
     * poolObject.markReturning
     */
    RETURNING
}
