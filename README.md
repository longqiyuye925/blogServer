# blogServer

## 登录

- [ ] 一个user表
- [x] token（为什么要用token?）
- [ ] redis缓存
- [x] 报文加解密
- [ ] 授权登录：QQ、微信
- [ ] 多次密码输入错误后，冻结账户
- [x] 权限
- [ ] 国际化
- [ ] swagger（可以替代postman）
- [ ] 账户过期
- [ ] 账户锁定

思路：

权限：

- Filter 
- AbstractSecurityInterceptor （权限管理security真正的拦截器，并绑定了AccessDecisionManager（处理权限认证）和FilterInvocationSecurityMetadataSource（提供请求路径和权限名称元数据） ）
- FilterInvocationSecurityMetadataSource （主要实现加载缓存权限功能路径和名称，以及提供从请求路径查找权限名称，供后续决策管理器去判定使用。）
- AccessDecisionManager（主要判定用户是否拥有权限内的决策方法，有权限放行，无权限拒绝访问。）
- WebSecurityConfigurerAdapter  （配置类）
- AccessDeniedHandler（跨域）
- 







问题：

1 @RequestParam接受Content-Type类型为application/json的请求时，报错：spring boot Required request parameter ‘xxx‘ for method parameter type xxx is not present。 **改为@RequestBody。Content-Type` : `application/x-www-form-urlencoded 时，使用@RequestParam。**

2 JWT 算法有这几种![image-20210615192844900](C:\Users\wxc\AppData\Roaming\Typora\typora-user-images\image-20210615192844900.png)



3 