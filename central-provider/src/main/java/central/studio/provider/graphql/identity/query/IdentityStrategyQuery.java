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

package central.studio.provider.graphql.identity.query;


import central.provider.graphql.DTO;
import central.bean.Page;
import central.studio.provider.graphql.identity.dto.IdentityStrategyDTO;
import central.studio.provider.graphql.identity.entity.IdentityStrategyEntity;
import central.studio.provider.graphql.identity.mapper.IdentityStrategyMapper;
import central.sql.query.Conditions;
import central.sql.query.Orders;
import central.starter.graphql.annotation.GraphQLBatchLoader;
import central.starter.graphql.annotation.GraphQLFetcher;
import central.starter.graphql.annotation.GraphQLSchema;
import central.web.XForwardedHeaders;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Identity Strategy
 * <p>
 * 认证策略
 *
 * @author Alan Yeh
 * @since 2022/11/05
 */
@Component
@GraphQLSchema(path = "identity/query", types = IdentityStrategyDTO.class)
public class IdentityStrategyQuery {
    @Setter(onMethod_ = @Autowired)
    private IdentityStrategyMapper mapper;

    /**
     * 批量数据加载器
     *
     * @param ids    主键
     * @param tenant 租户标识
     */
    @GraphQLBatchLoader
    public @Nonnull Map<String, IdentityStrategyDTO> batchLoader(@RequestParam List<String> ids,
                                                                 @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.mapper.findBy(Conditions.of(IdentityStrategyEntity.class).in(IdentityStrategyEntity::getId, ids).eq(IdentityStrategyEntity::getTenantCode, tenant))
                .stream()
                .map(it -> DTO.wrap(it, IdentityStrategyDTO.class))
                .collect(Collectors.toMap(IdentityStrategyDTO::getId, it -> it));
    }

    /**
     * 根据主键查询数据
     *
     * @param id     主键
     * @param tenant 租户标识
     */
    @GraphQLFetcher
    public @Nullable IdentityStrategyDTO findById(@RequestParam String id,
                                                  @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        var entity = this.mapper.findFirstBy(Conditions.of(IdentityStrategyEntity.class).eq(IdentityStrategyEntity::getId, id).eq(IdentityStrategyEntity::getTenantCode, tenant));
        return DTO.wrap(entity, IdentityStrategyDTO.class);
    }


    /**
     * 查询数据
     *
     * @param ids    主键
     * @param tenant 租户标识
     */
    @GraphQLFetcher
    public @Nonnull List<IdentityStrategyDTO> findByIds(@RequestParam List<String> ids,
                                                        @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        var entities = this.mapper.findBy(Conditions.of(IdentityStrategyEntity.class).in(IdentityStrategyEntity::getId, ids).eq(IdentityStrategyEntity::getTenantCode, tenant));

        return DTO.wrap(entities, IdentityStrategyDTO.class);
    }

    /**
     * 查询数据
     *
     * @param limit      获取前 N 条数据
     * @param offset     偏移量
     * @param conditions 过滤条件
     * @param orders     排序条件
     * @param tenant     租户标识
     */
    @GraphQLFetcher
    public @Nonnull List<IdentityStrategyDTO> findBy(@RequestParam(required = false) Long limit,
                                                     @RequestParam(required = false) Long offset,
                                                     @RequestParam Conditions<IdentityStrategyEntity> conditions,
                                                     @RequestParam Orders<IdentityStrategyEntity> orders,
                                                     @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        conditions = Conditions.group(conditions).eq(IdentityStrategyEntity::getTenantCode, tenant);
        var list = this.mapper.findBy(limit, offset, conditions, orders);
        return DTO.wrap(list, IdentityStrategyDTO.class);
    }

    /**
     * 分页查询数据
     *
     * @param pageIndex  分页下标
     * @param pageSize   分页大小
     * @param conditions 过滤条件
     * @param orders     排序条件
     * @param tenant     租户标识
     */
    @GraphQLFetcher
    public @Nonnull Page<IdentityStrategyDTO> pageBy(@RequestParam long pageIndex,
                                                     @RequestParam long pageSize,
                                                     @RequestParam Conditions<IdentityStrategyEntity> conditions,
                                                     @RequestParam Orders<IdentityStrategyEntity> orders,
                                                     @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        conditions = Conditions.group(conditions).eq(IdentityStrategyEntity::getTenantCode, tenant);
        var page = this.mapper.findPageBy(pageIndex, pageSize, conditions, orders);
        return DTO.wrap(page, IdentityStrategyDTO.class);
    }

    /**
     * 查询符合条件的数据数量
     *
     * @param conditions 筛选条件
     * @param tenant     租户标识
     */
    @GraphQLFetcher
    public Long countBy(@RequestParam Conditions<IdentityStrategyEntity> conditions,
                        @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        conditions = Conditions.group(conditions).eq(IdentityStrategyEntity::getTenantCode, tenant);
        return this.mapper.countBy(conditions);
    }
}
