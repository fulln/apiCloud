# apiCloud

### 组织架构
```
apiCloud
├─api
│  └─src
│    └─main
│        ├─java
│        │  └─com
│        │      └─fulln
│        │          └─me
│        │              └─api
│        │                  ├─common                           // 公共基础类
│        │                  │  ├─annotation                    // 公共使用到的注解
│        │                  │  ├─constant                      // 公共使用的常量
│        │                  │  ├─entity                        // 公共使用的实体
│        │                  │  ├─enums                         // 公共使用的枚举 
│        │                  │  │  ├─config                     // 配置相关枚举
│        │                  │  │  ├─downloads                  // 下载枚举
│        │                  │  │  ├─mongo                      // 使用mongogdb的枚举
│        │                  │  │  └─view                       // 页面浏览的时候使用的枚举
│        │                  │  ├─exception                     // 自定义异常
│        │                  │  ├─i18n                          // 自定义国际化
│        │                  │  ├─MongoDb                       // mongodb使用到的配置和bean
│        │                  │  ├─properties                    // 
│        │                  │  ├─threadconfig
│        │                  │  └─utils
│        │                  └─model
│        │                      ├─email
│        │                      ├─log
│        │                      ├─msg
│        │                      │  └─board
│        │                      ├─search
│        │                      └─system
│        │                          ├─cloums
│        │                          └─DTO
│        └─resources
│            └─Language
├─common
│  ├─config
│  │  └─src
│  │    └─main
│  │        ├─java
│  │        │  └─com
│  │        │      └─fulln
│  │        │          └─me
│  │        │              └─config
│  │        └─resources
│  ├─eureka
│  │  └─src
│  │    └─main
│  │        ├─java
│  │        │  └─com
│  │        │      └─fulln
│  │        │          └─me
│  │        │              └─eureka
│  │        │                  └─config
│  │        └─resources
│  └─eurekaSlave
│      └─src
│        └─main
│            ├─java
│            │  └─com
│            │      └─fulln
│            │          └─me
│            │              └─eurekaSlave
│            │                  └─config
│            └─resources

├─service
│  └─src
│    └─main
│        ├─java
│        │  └─com
│        │      └─fulln
│        │          └─me
│        │              ├─config
│        │              │  ├─basic
│        │              │  ├─dataSouece
│        │              │  ├─handle
│        │              │  └─redis
│        │              ├─constant
│        │              ├─controller
│        │              │  ├─basic
│        │              │  ├─log
│        │              │  ├─search
│        │              │  └─system
│        │              ├─dao
│        │              │  ├─es
│        │              │  ├─log
│        │              │  ├─msg
│        │              │  │  └─board
│        │              │  ├─search
│        │              │  └─system
│        │              ├─service
│        │              │  ├─basic
│        │              │  │  └─impl
│        │              │  ├─log
│        │              │  │  └─impl
│        │              │  ├─msg
│        │              │  │  └─board
│        │              │  │      └─Impl
│        │              │  ├─search
│        │              │  │  └─impl
│        │              │  └─system
│        │              │      └─impl
│        │              └─thread
│        └─resources
│            └─mapper
│                ├─search
│                └─system  
└─web
    ├─src
        └─main
            └─java
            └─com
               └─fulln
                    └─me
                        └─web
                           ├─config
                           │  ├─aop
                           │  ├─base
                           │  │  └─method
                           │  ├─constant
                           │  ├─cors
                           │  ├─ExceptionHandle
                           │  ├─intecepter
                           │  ├─redis
                           │  ├─shiro
                           │  ├─swagger2
                           │  └─websocket
                           ├─controller
                           │  ├─download
                           │  ├─guideview
                           │  ├─msg
                           │  │  └─board
                           │  ├─search
                           │  └─system
                           └─service
                                ├─basic
                                ├─log
                                ├─msg
                                │  └─board
                                ├─search
                                └─system
```