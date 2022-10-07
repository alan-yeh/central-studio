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

package central.provider.graphql.org;

import central.provider.graphql.org.mutation.*;
import central.starter.graphql.annotation.GraphQLGetter;
import central.starter.graphql.annotation.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Organization Mutation
 * 组织架构类修改
 *
 * @author Alan Yeh
 * @since 2022/10/03
 */
@Component
@GraphQLSchema(path = "org", types = {AreaMutation.class, UnitMutation.class, DepartmentMutation.class, RankMutation.class, PostMutation.class, AccountMutation.class})
public class OrgMutation {
    /**
     * Area Mutation
     * 行政区划修改
     */
    @GraphQLGetter
    public AreaMutation getAreas(@Autowired AreaMutation mutation) {
        return mutation;
    }

    /**
     * Unit Mutation
     * 单位修改
     */
    @GraphQLGetter
    public UnitMutation getUnits(@Autowired UnitMutation mutation) {
        return mutation;
    }

    /**
     * Department Mutation
     * 部门修改
     * TODO 这个是不是应该放到 UnitMutation 下
     */
    @GraphQLGetter
    public DepartmentMutation getDepartments(@Autowired DepartmentMutation mutation) {
        return mutation;
    }

    /**
     * Rank Mutation
     * 职级修改
     * TODO 这个是不是应该放到 UnitMutation 下
     */
    @GraphQLGetter
    public RankMutation getRanks(@Autowired RankMutation mutation) {
        return mutation;
    }

    /**
     * Post Mutation
     * 职务修改
     * TODO 这个是不是应该放到 UnitMutation 下
     */
    @GraphQLGetter
    public PostMutation getPosts(@Autowired PostMutation mutation) {
        return mutation;
    }

    /**
     * Account Mutation
     * 帐户修改
     */
    @GraphQLGetter
    public AccountMutation getAccounts(@Autowired AccountMutation mutation) {
        return mutation;
    }
}
