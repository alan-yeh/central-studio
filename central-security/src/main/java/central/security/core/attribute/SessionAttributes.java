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

package central.security.core.attribute;

import central.lang.Attribute;
import central.security.core.CookieManager;

import java.time.Duration;

/**
 * 会话配置
 *
 * @author Alan Yeh
 * @since 2023/02/15
 */
public interface SessionAttributes {
    /**
     * 会话超时时间
     */
    Attribute<Long> TIMEOUT = Attribute.of("session.timeout", () -> Duration.ofMinutes(30).toMillis());
    /**
     * 会话颁发者
     */
    Attribute<String> ISSUER = Attribute.of("session.issuer", "com.central-x.security");
    /**
     * 会话 Cookie
     */
    Attribute<CookieManager> COOKIE = Attribute.of("session.cookie", () -> new CookieManager("Authorization", null, true, false));
}
