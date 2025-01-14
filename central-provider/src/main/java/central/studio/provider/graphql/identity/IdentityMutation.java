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

package central.studio.provider.graphql.identity;

import central.studio.provider.graphql.identity.mutation.IdentityPasswordMutation;
import central.studio.provider.graphql.identity.mutation.IdentityStrategyMutation;
import central.starter.graphql.annotation.GraphQLGetter;
import central.starter.graphql.annotation.GraphQLSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Identity Mutation
 * <p>
 * 认证中心修改
 *
 * @author Alan Yeh
 * @since 2022/10/02
 */
@Component
@GraphQLSchema(path = "identity", types = {IdentityPasswordMutation.class, IdentityStrategyMutation.class})
public class IdentityMutation {

    /**
     * Password Mutation
     * <p>
     * 密码修改
     */
    @GraphQLGetter
    public IdentityPasswordMutation getPasswords(@Autowired IdentityPasswordMutation mutation) {
        return mutation;
    }

    /**
     * Strategy Mutation
     * <p>
     * 安全策略修改
     */
    @GraphQLGetter
    public IdentityStrategyMutation getStrategies(@Autowired IdentityStrategyMutation mutation) {
        return mutation;
    }
}
