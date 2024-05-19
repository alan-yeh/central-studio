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

package central.studio.logging.core.filter.predicate;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;

/**
 * Predicate Resolver
 * <p>
 * 断言解析
 *
 * @author Alan Yeh
 * @since 2024/05/19
 */
public interface PredicateResolver {
    /**
     * 根据标识和参数实例化断言
     *
     * @param code   类型标识
     * @param params 初始化参数
     * @return 断言实例，如果未找到标识对应的类型，则返回空
     */
    @Nullable
    Predicate resolve(@Nonnull String code, @Nonnull Map<String, Object> params);

    /**
     * 销毁断言实例
     *
     * @param predicate 实例
     */
    void destroy(@Nonnull Predicate predicate);
}
