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

package central.security.core;

import central.security.core.strategy.GlobalStrategy;
import central.security.core.strategy.StandardStrategyChain;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Security Exchange Dispatcher
 * 安全请求分发
 *
 * @author Alan Yeh
 * @since 2022/10/19
 */
@Component
public class SecurityDispatcher {
    /**
     * 全局安全策略
     */
    @Setter(onMethod_ = @Autowired)
    private List<GlobalStrategy> strategies;

    public void dispatch(SecurityExchange exchange) {
        new StandardStrategyChain(strategies).execute(exchange);
    }
}