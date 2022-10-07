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

package central.data.ten;

import central.bean.Available;
import central.bean.Remarkable;
import central.data.org.Account;
import central.sql.data.ModifiableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serial;

/**
 * 应用模块
 *
 * @author Alan Yeh
 * @since 2022/09/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApplicationModule extends ModifiableEntity implements Available, Remarkable {

    @Serial
    private static final long serialVersionUID = -4968804715373126621L;

    /**
     * 应用主键
     */
    @Nonnull
    private String applicationId;

    /**
     * 应用信息
     */
    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Application application;

    /**
     * 服务地址
     */
    @Nonnull
    private String url;

    /**
     * 上下文路径
     */
    @Nonnull
    private String contextPath;

    /**
     * 是否启用
     */
    @Nonnull
    private Boolean enabled;

    /**
     * 备注
     */
    @Nullable
    private String remark;

    /**
     * 创建人信息
     */
    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account creator;

    /**
     * 修改人信息
     */
    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Account modifier;

    public ApplicationModuleInput toInput() {
        return ApplicationModuleInput.builder()
                .id(this.getId())
                .url(this.getUrl())
                .contextPath(this.getContextPath())
                .applicationId(this.getApplicationId())
                .enabled(this.getEnabled())
                .remark(this.getRemark())
                .build();
    }
}
