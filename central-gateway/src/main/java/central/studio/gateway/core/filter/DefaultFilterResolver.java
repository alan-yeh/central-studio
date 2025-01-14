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

package central.studio.gateway.core.filter;

import central.pluglet.PlugletFactory;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Default Filter Resolver
 * <p>
 * 默认过滤器类型解析器
 *
 * @author Alan Yeh
 * @since 2024/04/24
 */
@RequiredArgsConstructor
public class DefaultFilterResolver implements FilterResolver {

    public final PlugletFactory factory;

    @Nullable
    @Override
    public Filter resolve(@NotNull String code, @NotNull Map<String, Object> params) {
        var type = FilterType.resolve(code);
        if (type == null) {
            return null;
        }

        return this.instance(type.getType(), params);
    }

    protected Filter instance(@Nonnull Class<? extends Filter> type, @Nonnull Map<String, Object> params) {
        return this.factory.create(type, params);
    }

    @Override
    public void destroy(@NotNull Filter filter) {
        this.factory.destroy(filter);
    }
}
