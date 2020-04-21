/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lmax.disruptor;

/**
 * Used to alert {@link EventProcessor}s waiting at a {@link SequenceBarrier} of status changes.
 * <p>
 * It does not fill in a stack trace for performance reasons.
 */
@SuppressWarnings("serial")
public final class AlertException extends Exception
{
    /**
     * Pre-allocated exception to avoid garbage generation
     */
    public static final AlertException INSTANCE = new AlertException();

    /**
     * Private constructor so only a single instance exists.
     */
    private AlertException()
    {
    }

    /**
     * Overridden so the stack trace is not filled in for this exception for performance reasons.
     * 只要那个自定义异常类型是真的不需要stack trace的，推荐覆写fillInStackTrace()为直接返回this
     * @return this instance.
     */
    @Override
    public Throwable fillInStackTrace()
    {
        return this;
    }
}
