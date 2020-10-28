package com.fulln.me.web.config.shiro;


import me.fulln.base.common.enums.RoleStatusEnums;
import me.fulln.base.common.enums.UserStatEnums;
import me.fulln.base.common.exception.CustomerLockAccountException;
import me.fulln.base.common.exception.DisableCustomerException;
import me.fulln.base.common.utils.AesUtil;
import me.fulln.base.common.utils.DateUtil;
import me.fulln.base.model.system.SysPermission;
import me.fulln.base.model.system.SysRole;
import me.fulln.base.model.user.SysUserBasic;
import com.fulln.me.web.config.constant.DefaultHeadConfig;
import com.fulln.me.web.config.redis.RedisUtil;
import com.fulln.me.web.service.system.ISysPermissionService;
import com.fulln.me.web.service.system.ISysRoleService;
import com.fulln.me.web.service.system.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @AUthor: fulln
 * @Description: 权限管理
 * @Date : Created in  0:33  2018/10/12.
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysPermissionService permissionService;


    @Resource
    private ISysRoleService sysRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DefaultHeadConfig headConfig;


    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
        // (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
        // 我这里是有session时效性验证的,所有在这边可以不用这个,权当写demo了
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principalCollection);
            SecurityUtils.getSubject().logout();
            return null;
        }


        SysUserBasic user = userService.selectByUsername(principalCollection.getPrimaryPrincipal().toString());

        SysRole role = sysRoleService.findById(user.getRoleId());

        List<SysPermission> resourcesList = permissionService.loadUserResources(user.getRoleId());
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        resourcesList.forEach((resouces) -> info.addStringPermission(resouces.getResourceUrl()));
        info.addRole(role.getRoleName());
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();

        SysUserBasic user = userService.selectByUsername(username);

        if (user == null) {
            //未知账号
            throw new UnknownAccountException();
        }
        if (UserStatEnums.ACCOUNT_UNACTIVATED == user.getStat()) {
            // 未授权账号
            throw new UnauthorizedException();
        }
        if (UserStatEnums.ACCOUNT_LOCK == user.getStat()) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        if (UserStatEnums.ACCOUNT_DELETED == user.getStat()) {

            // 账号被删除
            throw new DisabledAccountException();
        }

        SysRole role = sysRoleService.findById(user.getRoleId());

        //角色被锁定
        if (role.getRoleStatus() == RoleStatusEnums.ROLE_LOCK) {
            throw new CustomerLockAccountException();
        }
        //角色被删除
        if (role.getRoleStatus() == RoleStatusEnums.ROLE_DELETED) {
            throw new DisableCustomerException();
        }

        SimpleAuthenticationInfo
                //动态密码
                authenticationInfo = new SimpleAuthenticationInfo(
                //用户
                user.getUserName(),
                //密码
                user.getUserPass(),
                ByteSource.Util.bytes(user.getUserSalt()),
                //realm name
                getName()
        );


        String currentToken = AesUtil.AESEncode(user.getUserId() + "" + DateUtil.getNowDateStamp());

        user.setCurrentToken(currentToken);
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();


        session.setAttribute("currentUser", user);
        session.setAttribute("currentUserId", user.getUserId());

        return authenticationInfo;
    }


}
