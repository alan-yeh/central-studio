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

import central.bean.Nullable;
import central.bean.OptionalEnum;
import central.data.organization.Account;
import central.lang.Arrayx;
import central.sql.query.Conditions;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * 登录字段
 *
 * @author Alan Yeh
 * @since 2023/11/20
 */
@Getter
@AllArgsConstructor
public enum LoginField implements OptionalEnum<String> {
    USERNAME("用户名", "username", (account, conditions) -> {
        if (!conditions.isEmpty()) {
            conditions.or().eq(Account::getUsername, account.getUsername());
        } else {
            conditions.eq(Account::getUsername, account.getUsername());
        }
    }),
    MOBILE("手机号", "mobile", (account, conditions) -> {
        if (!conditions.isEmpty()) {
            conditions.or().eq(Account::getMobile, account.getMobile());
        } else {
            conditions.eq(Account::getMobile, account.getMobile());
        }
    }),
    EMAIL("邮箱", "email", (account, conditions) -> {
        if (!conditions.isEmpty()) {
            conditions.or().eq(Account::getEmail, account.getEmail());
        } else {
            conditions.eq(Account::getEmail, account.getEmail());
        }
    });

    private final String name;
    private final String value;
    private final BiConsumer<Account, Conditions<Account>> filter;

    @Override
    public String toString() {
        return this.value;
    }

    @JsonCreator
    public static @Nullable LoginField resolve(String value) {
        return Arrayx.asStream(LoginField.values()).filter(it -> Objects.equals(it.getValue(), value)).findFirst().orElse(null);
    }
}
