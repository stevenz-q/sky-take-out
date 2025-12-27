# sky-take-out

#### 介绍

 **苍穹外卖**

简易的外卖点单系统

#### 技术栈

后端: SpringBoot + MyBatis + MySQL + Redis + AliOSS + WebSocket + baidu Geocoding

前端: admin: vue2 + axios + element-ui + echarts

用户端: mp-weixin

#### 更新日志

v2.0.0 @2025-12-20

- 优化对象存储服务，通过抽象存储接口，支持对象存储服务的可插拔切换（OSS / MinIO）
- 基于 Spring @ConditionalOnProperty 的条件装配实现对象存储（MinIO/阿里 OSS）可插拔，按 application.yml 的 storage.type 配置即可无代码切换并自动注入对应客户端与 StorageService 实现。
- 基于策略模式抽象对象存储能力，并通过适配层封装 MinIO/OSS SDK 差异，配合 @ConditionalOnProperty 实现配置驱动切换
- 适配器模式：两种实现内部把各自 SDK（MinioClient、OSS）的调用差异（上传/删除、参数格式、返回 URL 拼接）适配成统一的 upload/delete 能力，对业务层屏蔽厂商差异。

v2.1.0 @2025-12-27

- 引入Sa-Token权限认证框架，简化认证流程
- Sa-Token 集成 Redis，实现重启数据不丢失，而且保证分布式环境下多节点的会话一致性（可扩展性）
- 重构管理端系统认证体系，从JWT无状态认证方案迁移至Sa-Token + Redis有状态会话模型，通过服务端集中管理登录态与权限信息，实现多身份体系隔离、实时踢人、权限动态生效及分布式环境下的一致性会话控制。

#### TODO

- 重构用户端系统认证体系，基于 Sa-Token 实现多登录体系分别注册独立的 StpLogic Bean，并为不同 loginType 定制 token-name，实现小程序用户端与 H5 管理端在 Header、Token 与 Session 层面的完全隔离。
- 增加角色管理（店长/店员），实现权限控制（店长可管理所有员工，店员只能修改自己的密码）

#### 改造计划

v2.2.0 @Future

- 系统多租户SaaS改造，实现租户间数据完全隔离，通过MyBatis-Plus多租户拦截器实现