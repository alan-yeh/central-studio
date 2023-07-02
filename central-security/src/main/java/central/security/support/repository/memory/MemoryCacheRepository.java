/*
 * MIT License
 *
 * Copyright (c) 2022-present Alan Yeh <alan@yeh.cn>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package central.security.support.repository.memory;

import central.security.support.repository.*;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 内存缓存仓库
 *
 * @author Alan Yeh
 * @since 2023/06/10
 */
public class MemoryCacheRepository implements CacheRepository {
    @Override
    public boolean hasKey(String key) {
        return false;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public long delete(Collection<String> keys) {
        return 0;
    }

    @Override
    public DataType type(String key) {
        return null;
    }

    @Override
    public Set<String> keys() {
        return null;
    }

    @Override
    public boolean expire(String key, Duration timeout) {
        return false;
    }

    @Override
    public boolean expireAt(String key, Date date) {
        return false;
    }

    @Override
    public boolean persist(String key) {
        return false;
    }

    @Override
    public Duration getExpire(String key) {
        return null;
    }

    @Override
    public CacheValue opsValue(String key) {
        return null;
    }

    @Override
    public CacheList opsList(String key) {
        return null;
    }

    @Override
    public CacheQueue opsQueue(String key) {
        return null;
    }

    @Override
    public CacheSet opsSet(String key) {
        return null;
    }

    @Override
    public CacheSet opsZSet(String key) {
        return null;
    }

    @Override
    public CacheMap opsMap(String key) {
        return null;
    }
}
