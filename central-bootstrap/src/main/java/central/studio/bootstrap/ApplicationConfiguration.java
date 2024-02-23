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

package central.studio.bootstrap;

import central.studio.dashboard.EnableCentralStudioDashboard;
import central.studio.identity.EnableCentralStudioIdentity;
import central.studio.logging.EnableCentralStudioLogging;
import central.studio.multicast.EnableCentralStudioMulticast;
import central.studio.provider.EnableCentralStudioProvider;
import central.studio.storage.EnableCentralStudioStorage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application Configuration
 *
 * @author Alan Yeh
 * @since 2024/01/20
 */
@Configuration
// Central Studio
@EnableCentralStudioLogging // 日志中心
@EnableCentralStudioProvider // 数据服务中心
@EnableCentralStudioStorage // 存储中心
@EnableCentralStudioMulticast // 广播中心
@EnableCentralStudioIdentity // 认证中心
@EnableCentralStudioDashboard // 控制中心
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {
}
