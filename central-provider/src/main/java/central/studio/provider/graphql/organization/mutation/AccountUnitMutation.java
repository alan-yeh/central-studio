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

package central.studio.provider.graphql.organization.mutation;

import central.provider.graphql.DTO;
import central.data.organization.AccountUnitInput;
import central.studio.provider.graphql.organization.dto.AccountUnitDTO;
import central.studio.provider.graphql.organization.entity.AccountUnitEntity;
import central.studio.provider.graphql.organization.mapper.AccountUnitMapper;
import central.sql.query.Conditions;
import central.starter.graphql.annotation.GraphQLFetcher;
import central.starter.graphql.annotation.GraphQLGetter;
import central.starter.graphql.annotation.GraphQLSchema;
import central.web.XForwardedHeaders;
import central.util.Listx;
import central.validation.group.Insert;
import jakarta.annotation.Nonnull;
import jakarta.validation.groups.Default;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * AccountUnit Mutation
 * 帐户单位关联关系修改
 *
 * @author Alan Yeh
 * @since 2022/10/04
 */
@Component
@GraphQLSchema(path = "organization/mutation", types = {AccountUnitDTO.class, AccountDepartmentMutation.class})
public class AccountUnitMutation {

    @Setter(onMethod_ = @Autowired)
    private AccountUnitMapper mapper;

    /**
     * 保存数据
     *
     * @param input    数据输入
     * @param operator 操作人
     * @param tenant   租户标识
     */
    @GraphQLFetcher
    public @Nonnull AccountUnitDTO insert(@RequestParam @Validated({Insert.class, Default.class}) AccountUnitInput input,
                                          @RequestParam String operator,
                                          @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        var entity = new AccountUnitEntity();
        entity.fromInput(input);
        entity.setTenantCode(tenant);
        entity.updateCreator(operator);
        this.mapper.insert(entity);

        return DTO.wrap(entity, AccountUnitDTO.class);
    }

    /**
     * 批量保存数据
     *
     * @param inputs   数据输入
     * @param operator 操作人
     * @param tenant   租户标识
     */
    @GraphQLFetcher
    public @Nonnull List<AccountUnitDTO> insertBatch(@RequestParam @Validated({Insert.class, Default.class}) List<AccountUnitInput> inputs,
                                                     @RequestParam String operator,
                                                     @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return Listx.asStream(inputs).map(it -> this.insert(it, operator, tenant)).toList();
    }

    /**
     * 根据主键删除数据
     *
     * @param ids    主键
     * @param tenant 租户标识
     */
    @GraphQLFetcher
    public long deleteByIds(@RequestParam List<String> ids,
                            @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        if (Listx.isNullOrEmpty(ids)) {
            return 0;
        }

        return this.mapper.deleteBy(Conditions.of(AccountUnitEntity.class).in(AccountUnitEntity::getId, ids).eq(AccountUnitEntity::getTenantCode, tenant));
    }

    /**
     * 根据条件删除数据
     *
     * @param conditions 条件
     * @param tenant     租户标识
     */
    @GraphQLFetcher
    public long deleteBy(@RequestParam Conditions<AccountUnitEntity> conditions,
                         @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        conditions = Conditions.group(conditions).eq(AccountUnitEntity::getTenantCode, tenant);
        return this.mapper.deleteBy(conditions);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 关联查询
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Account Department Mutation
     * 帐户与部门关联关系修改
     */
    @GraphQLGetter
    public AccountDepartmentMutation getDepartments(@Autowired AccountDepartmentMutation mutation) {
        return mutation;
    }
}
