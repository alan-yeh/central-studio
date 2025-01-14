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

package central.studio.dashboard.controller.organization.controller;

import central.data.organization.Department;
import central.data.organization.Unit;
import central.starter.web.param.IdsParams;
import central.starter.web.query.IdQuery;
import central.studio.dashboard.controller.organization.param.DepartmentParams;
import central.studio.dashboard.controller.organization.param.UnitParams;
import central.studio.dashboard.controller.organization.query.DepartmentListQuery;
import central.studio.dashboard.controller.organization.query.UnitListQuery;
import central.studio.dashboard.logic.organization.UnitLogic;
import central.validation.group.Insert;
import central.validation.group.Update;
import central.web.XForwardedHeaders;
import jakarta.validation.groups.Default;
import lombok.Setter;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Unit Controller
 * <p>
 * 组织机构管理
 *
 * @author Alan Yeh
 * @since 2024/09/16
 */
@RestController
@RequiresAuthentication
@RequestMapping("/dashboard/api/organization/units")
public class UnitController {

    public interface Permissions {
        String VIEW = "organization:unit:view";
        String ADD = "organization:unit:add";
        String EDIT = "organization:unit:edit";
        String DELETE = "organization:unit:delete";


        String DEPARTMENT_VIEW = "organization:unit:department:view";
        String DEPARTMENT_ADD = "organization:unit:department:add";
        String DEPARTMENT_EDIT = "organization:unit:department:edit";
        String DEPARTMENT_DELETE = "organization:unit:department:delete";
    }

    @Setter(onMethod_ = @Autowired)
    private UnitLogic logic;

    /**
     * 按条件查询组织机构列表
     *
     * @param query  查询
     * @param tenant 租户标识
     * @return 分页结果
     */
    @GetMapping
    public List<Unit> list(@Validated UnitListQuery query, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.listBy(query.build(), null, tenant);
    }

    /**
     * 根据主键查询组织机构详情
     *
     * @param query  查询
     * @param tenant 租户标识
     * @return 详情
     */
    @GetMapping("/details")
    public Unit details(@Validated IdQuery<Unit> query, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.findById(query.getId(), tenant);
    }

    /**
     * 新增组织机构
     *
     * @param params    组织机构入参
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 新增后组织机构数据
     */
    @PostMapping
    public Unit add(@RequestBody @Validated({Insert.class, Default.class}) UnitParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.insert(params.toInput(), accountId, tenant);
    }

    /**
     * 更新组织机构
     *
     * @param params    组织机构数据
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 更新后组织机构数据
     */
    @PutMapping
    public Unit update(@RequestBody @Validated({Update.class, Default.class}) UnitParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.update(params.toInput(), accountId, tenant);
    }

    /**
     * 根据主键删除组织机构数据
     *
     * @param params    组织机构数据
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 受影响数据行数
     */
    @DeleteMapping
    public long delete(@Validated IdsParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.deleteByIds(params.getIds(), accountId, tenant);
    }

    /**
     * 按条件查询组织机构列表
     *
     * @param query  查询
     * @param tenant 租户标识
     * @return 分页结果
     */
    @GetMapping("/departments")
    public List<Department> listDepartments(@Validated DepartmentListQuery query, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.listDepartmentBy(query.build(), null, tenant);
    }

    /**
     * 根据主键查询组织机构详情
     *
     * @param query  查询
     * @param tenant 租户标识
     * @return 详情
     */
    @GetMapping("/departments/details")
    public Department detailsDepartment(@Validated IdQuery<Department> query, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.findDepartmentById(query.getId(), tenant);
    }

    /**
     * 新增组织机构
     *
     * @param params    组织机构入参
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 新增后组织机构数据
     */
    @PostMapping("/departments")
    public Department addDepartment(@RequestBody @Validated({Insert.class, Default.class}) DepartmentParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.insertDepartment(params.toInput(), accountId, tenant);
    }

    /**
     * 更新组织机构
     *
     * @param params    组织机构数据
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 更新后组织机构数据
     */
    @PutMapping("/departments")
    public Department updateDepartment(@RequestBody @Validated({Update.class, Default.class}) DepartmentParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.updateDepartment(params.toInput(), accountId, tenant);
    }

    /**
     * 根据主键删除组织机构数据
     *
     * @param params    组织机构数据
     * @param accountId 当前登录帐号
     * @param tenant    租户标识
     * @return 受影响数据行数
     */
    @DeleteMapping("/departments")
    public long deleteDepartment(@Validated IdsParams params, @RequestAttribute String accountId, @RequestHeader(XForwardedHeaders.TENANT) String tenant) {
        return this.logic.deleteDepartmentByIds(params.getIds(), accountId, tenant);
    }

}
