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

package central.provider.graphql.ten;

import central.api.provider.ten.ApplicationProvider;
import central.data.ten.Application;
import central.data.ten.ApplicationInput;
import central.provider.ApplicationProperties;
import central.provider.ProviderApplication;
import central.provider.graphql.TestProvider;
import central.provider.graphql.ten.entity.ApplicationEntity;
import central.provider.graphql.ten.entity.ApplicationModuleEntity;
import central.provider.graphql.ten.mapper.ApplicationMapper;
import central.provider.graphql.ten.mapper.ApplicationModuleMapper;
import central.sql.Conditions;
import central.util.Guidx;
import central.util.Listx;
import lombok.Setter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Application Provider Test Cases
 * 应用
 *
 * @author Alan Yeh
 * @since 2022/09/28
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = ProviderApplication.class)
public class TestApplicationProvider extends TestProvider {

    @Setter(onMethod_ = @Autowired)
    private ApplicationProvider provider;

    @Setter(onMethod_ = @Autowired)
    private ApplicationProperties properties;

    @Setter(onMethod_ = @Autowired)
    private ApplicationMapper mapper;

    @Setter(onMethod_ = @Autowired)
    private ApplicationModuleMapper moduleMapper;

    @BeforeEach
    @AfterEach
    public void clear() {
        // 清空数据
        mapper.deleteAll();
        moduleMapper.deleteAll();
    }

    /**
     * @see ApplicationProvider#findById
     */
    @Test
    public void case1() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        // 查询数据
        var application = this.provider.findById(entity.getId());
        assertNotNull(application);
        assertEquals(entity.getId(), application.getId());
        assertEquals(entity.getCode(), application.getCode());
        assertEquals(Base64.getEncoder().encodeToString(entity.getLogoBytes()), application.getLogo());
        assertEquals(entity.getUrl(), application.getUrl());
        assertEquals(entity.getContextPath(), application.getContextPath());
        assertEquals(entity.getKey(), application.getKey());
        assertEquals(entity.getEnabled(), application.getEnabled());
        assertEquals(entity.getRemark(), application.getRemark());
        assertNotNull(application.getModules());
        assertEquals(2, application.getModules().size());
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module1.getId(), it.getId())));
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module2.getId(), it.getId())));
    }

    /**
     * @see ApplicationProvider#findByIds
     */
    @Test
    public void case2() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        // 查询数据
        var applications = this.provider.findByIds(List.of(entity.getId()));
        var application = Listx.getFirstOrNull(applications);
        assertNotNull(application);
        assertEquals(entity.getId(), application.getId());
        assertEquals(entity.getCode(), application.getCode());
        assertEquals(Base64.getEncoder().encodeToString(entity.getLogoBytes()), application.getLogo());
        assertEquals(entity.getUrl(), application.getUrl());
        assertEquals(entity.getContextPath(), application.getContextPath());
        assertEquals(entity.getKey(), application.getKey());
        assertEquals(entity.getEnabled(), application.getEnabled());
        assertEquals(entity.getRemark(), application.getRemark());
        assertNotNull(application.getModules());
        assertEquals(2, application.getModules().size());
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module1.getId(), it.getId())));
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module2.getId(), it.getId())));
    }

    /**
     * @see ApplicationProvider#findBy
     */
    @Test
    public void case3() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        // 查询数据
        var applications = this.provider.findBy(1L, 0L, Conditions.of(Application.class).eq(Application::getCode, "central-security"), null);
        var application = Listx.getFirstOrNull(applications);
        assertNotNull(application);
        assertEquals(entity.getId(), application.getId());
        assertEquals(entity.getCode(), application.getCode());
        assertEquals(Base64.getEncoder().encodeToString(entity.getLogoBytes()), application.getLogo());
        assertEquals(entity.getUrl(), application.getUrl());
        assertEquals(entity.getContextPath(), application.getContextPath());
        assertEquals(entity.getKey(), application.getKey());
        assertEquals(entity.getEnabled(), application.getEnabled());
        assertEquals(entity.getRemark(), application.getRemark());
        assertNotNull(application.getModules());
        assertEquals(2, application.getModules().size());
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module1.getId(), it.getId())));
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module2.getId(), it.getId())));
    }

    /**
     * @see ApplicationProvider#pageBy
     */
    @Test
    public void case4() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        // 查询数据
        var page = this.provider.pageBy(1L, 20L, Conditions.of(Application.class).eq(Application::getCode, "central-security"), null);
        assertNotNull(page);
        assertNotNull(page.getPager());
        assertEquals(1L, page.getPager().getPageIndex());
        assertEquals(20L, page.getPager().getPageSize());
        assertEquals(1L, page.getPager().getPageCount());
        assertEquals(1L, page.getPager().getItemCount());
        assertNotNull(page.getData());
        var application = Listx.getFirstOrNull(page.getData());
        assertNotNull(application);
        assertEquals(entity.getId(), application.getId());
        assertEquals(entity.getCode(), application.getCode());
        assertEquals(Base64.getEncoder().encodeToString(entity.getLogoBytes()), application.getLogo());
        assertEquals(entity.getUrl(), application.getUrl());
        assertEquals(entity.getContextPath(), application.getContextPath());
        assertEquals(entity.getKey(), application.getKey());
        assertEquals(entity.getEnabled(), application.getEnabled());
        assertEquals(entity.getRemark(), application.getRemark());
        assertNotNull(application.getModules());
        assertEquals(2, application.getModules().size());
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module1.getId(), it.getId())));
        assertTrue(application.getModules().stream().anyMatch(it -> Objects.equals(module2.getId(), it.getId())));
    }

    /**
     * @see ApplicationProvider#countBy
     */
    @Test
    public void case5() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        // 查询数据
        var count = this.provider.countBy(Conditions.of(Application.class).eq(Application::getCode, "central-security"));
        assertEquals(1L, count);
    }

    /**
     * @see ApplicationProvider#insert
     */
    @Test
    public void case6() {
        var input = ApplicationInput.builder()
                .code("central-security")
                .name("统一认证中心")
                .logo(Base64.getEncoder().encodeToString("1234".getBytes(StandardCharsets.UTF_8)))
                .url("http://127.0.0.1:3100")
                .contextPath("/security")
                .key(Guidx.nextID())
                .enabled(Boolean.TRUE)
                .remark("用于所有应用的认证处理")
                .build();
        var application = this.provider.insert(input, properties.getSupervisor().getUsername());
        assertNotNull(application);
        assertNotNull(application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());

        application = this.provider.findById(application.getId());
        assertNotNull(application);
        assertNotNull(application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());
    }

    /**
     * @see ApplicationProvider#insertBatch
     */
    @Test
    public void case7() {
        var input = ApplicationInput.builder()
                .code("central-security")
                .name("统一认证中心")
                .logo(Base64.getEncoder().encodeToString("1234".getBytes(StandardCharsets.UTF_8)))
                .url("http://127.0.0.1:3100")
                .contextPath("/security")
                .key(Guidx.nextID())
                .enabled(Boolean.TRUE)
                .remark("用于所有应用的认证处理")
                .build();
        var applications = this.provider.insertBatch(List.of(input), properties.getSupervisor().getUsername());
        assertNotNull(applications);
        assertEquals(1, applications.size());

        var application = Listx.getFirstOrNull(applications);

        assertNotNull(application);
        assertNotNull(application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());

        application = this.provider.findById(application.getId());
        assertNotNull(application);
        assertNotNull(application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());
    }

    /**
     * @see ApplicationProvider#update
     */
    @Test
    public void case8() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        var application = this.provider.findById(entity.getId());
        assertNotNull(application);

        var input = application.toInput().toBuilder()
                .name("统一认证")
                .url("http://127.0.0.1:4100")
                .build();

        application = this.provider.update(input, properties.getSupervisor().getUsername());
        assertEquals(input.getId(), application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());
    }

    /**
     * @see ApplicationProvider#updateBatch
     */
    @Test
    public void case9() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        var application = this.provider.findById(entity.getId());
        assertNotNull(application);

        var input = application.toInput().toBuilder()
                .name("统一认证")
                .url("http://127.0.0.1:4100")
                .build();

        var updated = this.provider.updateBatch(List.of(input), properties.getSupervisor().getUsername());
        application = Listx.getFirstOrNull(updated);
        assertNotNull(application);
        assertEquals(input.getId(), application.getId());
        assertEquals(input.getCode(), application.getCode());
        assertEquals(input.getLogo(), application.getLogo());
        assertEquals(input.getUrl(), application.getUrl());
        assertEquals(input.getContextPath(), application.getContextPath());
        assertEquals(input.getKey(), application.getKey());
        assertEquals(input.getEnabled(), application.getEnabled());
        assertEquals(input.getRemark(), application.getRemark());
    }

    /**
     * @see ApplicationProvider#deleteByIds
     */
    @Test
    public void case10() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        var deleted = this.provider.deleteByIds(List.of(entity.getId()));
        assertNotNull(deleted);
        assertEquals(1L, deleted);

        assertFalse(this.mapper.existsBy(Conditions.of(ApplicationEntity.class).eq(ApplicationEntity::getId, entity.getId())));
        // 模块需要关联删除
        assertFalse(this.moduleMapper.existsBy(Conditions.of(ApplicationModuleEntity.class).eq(ApplicationModuleEntity::getApplicationId, entity.getId())));
    }

    /**
     * @see ApplicationProvider#deleteBy
     */
    @Test
    public void case11() {
        var entity = new ApplicationEntity();
        entity.setCode("central-security");
        entity.setName("统一认证中心");
        entity.setLogoBytes("1234".getBytes(StandardCharsets.UTF_8));
        entity.setUrl("http://127.0.0.1:3100");
        entity.setContextPath("/security");
        entity.setKey(Guidx.nextID());
        entity.setEnabled(Boolean.TRUE);
        entity.setRemark("用于所有应用的认证处理");
        entity.updateCreator(properties.getSupervisor().getUsername());
        this.mapper.insert(entity);

        var modules = new ArrayList<ApplicationModuleEntity>();

        var module1 = new ApplicationModuleEntity();
        module1.setApplicationId(entity.getId());
        module1.setUrl("http://127.0.0.1:3110");
        module1.setContextPath("/security/test");
        module1.setEnabled(Boolean.TRUE);
        module1.setRemark("测试模块1");
        module1.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module1);

        var module2 = new ApplicationModuleEntity();
        module2.setApplicationId(entity.getId());
        module2.setUrl("http://127.0.0.1:3120");
        module2.setContextPath("/security/example");
        module2.setEnabled(Boolean.TRUE);
        module2.setRemark("测试模块2");
        module2.updateCreator(properties.getSupervisor().getUsername());
        modules.add(module2);
        this.moduleMapper.insertBatch(modules);

        var deleted = this.provider.deleteBy(Conditions.of(Application.class).eq(Application::getCode, "central-security"));
        assertNotNull(deleted);
        assertEquals(1L, deleted);

        assertFalse(this.mapper.existsBy(Conditions.of(ApplicationEntity.class).eq(ApplicationEntity::getId, entity.getId())));
        // 模块需要关联删除
        assertFalse(this.moduleMapper.existsBy(Conditions.of(ApplicationModuleEntity.class).eq(ApplicationModuleEntity::getApplicationId, entity.getId())));
    }
}
