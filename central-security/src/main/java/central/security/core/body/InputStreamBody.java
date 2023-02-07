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

package central.security.core.body;

import central.io.IOStreamx;
import central.security.core.SecurityExchange;
import central.security.core.SecurityResponseBody;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.InputStream;

/**
 * InputStream Body
 * 数据流响应体
 *
 * @author Alan Yeh
 * @since 2022/10/19
 */
public class InputStreamBody implements SecurityResponseBody {

    @Getter
    private final HttpHeaders headers = new HttpHeaders();

    private final InputStream body;

    @SneakyThrows
    public InputStreamBody(InputStream body, MediaType contentType) {
        this.body = body;
        this.headers.setContentType(contentType);
        this.headers.setContentLength(body.available());
    }

    @Override
    public void write(SecurityExchange exchange) throws IOException {
        try (this.body) {
            IOStreamx.transfer(this.body, exchange.getResponse().getResponse().getOutputStream());
        }
    }
}
