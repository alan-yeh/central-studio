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

package central.logging.core.filter.predicate.impl;

import central.data.log.Log;
import central.data.log.option.LogLevel;
import central.logging.core.filter.predicate.Predicate;
import central.pluglet.annotation.Control;
import central.pluglet.control.ControlType;
import central.validation.Label;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 日志等级断言
 *
 * @author Alan Yeh
 * @since 2022/10/27
 */
public class LevelPredicate implements Predicate, InitializingBean {

    @Label("等级")
    @NotEmpty
    @Control(label = "等级", comment = "只留下指定等级的日志", type = ControlType.CHECKBOX, defaultValue = "info")
    private List<LogLevel> levels;

    private Set<String> level;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.level = levels.stream().map(LogLevel::getValue).collect(Collectors.toSet());
    }

    @Override
    public boolean predicate(Log log) {
        return this.level.contains(log.getLevel());
    }
}
