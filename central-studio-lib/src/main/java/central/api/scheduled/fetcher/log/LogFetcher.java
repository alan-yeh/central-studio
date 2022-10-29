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

package central.api.scheduled.fetcher.log;

import central.api.provider.log.LogCollectorProvider;
import central.api.provider.log.LogFilterProvider;
import central.api.provider.log.LogStorageProvider;
import central.api.scheduled.ProviderSupplier;
import central.api.scheduled.fetcher.DataFetcher;
import central.data.log.LogCollector;
import central.data.log.LogFilter;
import central.data.log.LogStorage;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 日志数据
 *
 * @author Alan Yeh
 * @since 2022/10/25
 */
public class LogFetcher implements DataFetcher<LogContainer> {

    @Setter
    private ProviderSupplier providerSupplier;

    @Getter
    private final long timeout = Duration.ofSeconds(5).toMillis();

    @Override
    public LogContainer get() {
        if (providerSupplier == null) {
            return new LogContainer();
        }

        // 获取采集器数据
        var collectorProvider = providerSupplier.get(LogCollectorProvider.class);
        var collectorData = collectorProvider.findBy(null, null, null, null);
        var collectors = collectorData.stream().collect(Collectors.toMap(LogCollector::getId, Function.identity()));

        // 获取过滤器数据
        var filterProvider = providerSupplier.get(LogFilterProvider.class);
        var filterData = filterProvider.findBy(null, null, null, null);
        var filters = filterData.stream().collect(Collectors.toMap(LogFilter::getId, Function.identity()));

        // 获取存储器数据
        var storageProvider = providerSupplier.get(LogStorageProvider.class);
        var storageData = storageProvider.findBy(null, null, null, null);
        var storages = storageData.stream().collect(Collectors.toMap(LogStorage::getId, Function.identity()));

        return new LogContainer(collectors, filters, storages);
    }
}
