const plugs = [
  {
    type: "身份认证",
    content: [
      {
        name: "Jwt-auth",
        code: "jwt",
        imgUrl: require('@/assets/pluginImgs/icon_jwt-auth@2x.png'),
        icon: require('@/assets/pluginIcon/icon_jwt-auth@2x.png'),
        message: "验证和认证JSON Web令牌",
        bigMessage: "jwt-auth鉴权插件用于验证包含 HS256 签名的 JSON Web 令牌的请求。该插件检查Authorization请求头中的用户凭据。",
        defaultShow: true,
        configuration: true
      },
      {
        name: "basic_auth",
        code: "base_auth",
        imgUrl: require('@/assets/pluginImgs/icon_basic-auth@2x.png'),
        icon: require('@/assets/pluginIcon/icon_basic-auth@2x.png'),
        message: "为您的服务添加基本身份验证",
        bigMessage: "basic-auth鉴权插件用于给调用服务的用户添加基本身份认证。该插件检查Authorization请求头中的用户凭据。",
        defaultShow: true,
        configuration: false
      },
      {
        name: "OAuth2.0",
        code: "oauth2",
        imgUrl: require('@/assets/pluginImgs/icon_oauth2.0@2x.png'),
        icon: require('@/assets/pluginIcon/icon_oauth2.0@2x.png'),
        message: "将SDX与第三方OAuth2.0授权服务器集成",
        bigMessage: "OAuth2鉴权插件用于给服务接口请求提供基于OAuth2协议的鉴权。该插件检查Authorization请求头中的用户凭据。",
        defaultShow: true,
        configuration: true
      },
    ],
  },
  {
    type: "安全防护",
    content: [
      {
        name: "IP 黑名单控制",
        code: "black_list_ip",
        imgUrl: require('@/assets/pluginImgs/icon_ip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_ipblack@2x.png'),
        message: "可以发出请求的黑名单",
        bigMessage: "为限制访问资源的用户，提升服务的安全性，您可以设置黑名单，实现对访客身份的识别和过滤。添加IP到黑名单，那么该IP无法访问当前服务。",
        defaultShow: true,
        configuration: true

      },
      {
        name: "IP 白名单控制",
        code: "white_list_ip",
        imgUrl: require('@/assets/pluginImgs/icon_ip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_ipblack@2x.png'),
        message: "可以发出请求的白名单",
        bigMessage: "为限制访问资源的用户，提升服务的安全性，您可以设置白名单，实现对访客身份的识别和过滤。添加IP到白名单，那么只有该IP能够访问当前服务。",
        defaultShow: true,
        configuration: true
      },
      {
        name: "cors跨域",
        code: "cors",
        imgUrl: require('@/assets/pluginImgs/icon_cors@2x.png'),
        icon: require('@/assets/pluginIcon/icon_cors@2x.png'),
        message: "允许开发人员从浏览器发出请求",
        bigMessage: "CORS跨域插件用于动态配置接口跨域资源访问，关于CORS跨域资源访问的基础知识可参考跨域资源共享（CORS）机制。",
        defaultShow: true,
        configuration: true
      },
      {
        name: "防篡改签名",
        code: "sign",
        imgUrl: require('@/assets/pluginImgs/icon_sign@2x.png'),
        icon: require('@/assets/pluginIcon/icon_sign@2x.png'),
        message: "-",
        bigMessage: "sign-auth插件用于保护请求数据中途不被篡改，相当于您给API网关颁发了一个token，开启插件后，请求接口时需要根据约定的加签处理方法和key生成签名并放到请求头中，网关会对比签名和服务器端计算的签名是否一致，实现身份验证。",
        defaultShow: true,
        configuration: true

      },
      {
        name: '防网络重放攻击',
        code: 'replay_attacks',
        imgUrl: require('@/assets/pluginImgs/icon_attcks@2x.png'),
        icon: require('@/assets/pluginIcon/icon_replay_attacks@2x.png'),
        message: '网络请求重试限制',
        bigMessage: "防网络重放攻击",
        defaultShow: true,
        configuration: false
      }
    ],
  },
  {
    type: '可观测性',
    content: [
      {
        name: 'error log',
        code: 'error_log',
        imgUrl: require('@/assets/pluginImgs/icon_error_log@2x.png'),
        icon: require('@/assets/pluginIcon/icon_error_log@2x.png'),
        message: '错误日志记录器',
        bigMessage: "添加此插件可观测服务调用失败的日志",
        defaultShow: true,
        configuration: false
      },
      {
        name: 'http log',
        code: 'http_log',
        imgUrl: require('@/assets/pluginImgs/icon_http_log @2x.png'),
        icon: require('@/assets/pluginIcon/icon_http_log @2x.png'),
        message: '将请求和响应日志发送到http服务器',
        bigMessage: "添加此插件可观测服务调用成功的日志",
        defaultShow: true,
        configuration: false
      }
    ]
  },
  {
    type: '流量控制',
    content: [
      {
        name: 'sentinel',
        code: 'sentinel',
        imgUrl: require('@/assets/pluginImgs/icon_sentinel@2x.png'),
        icon: require('@/assets/pluginIcon/icon_sentinel@2x.png'),
        message: '外部系统集成控流插件',
        bigMessage: "对服务和服务下的api进行流量控制，限流规则持久化到nacos配置中心，网关服务监听该配置文件，实时更新生效",
        defaultShow: true,
        configuration: true
      }
    ]
  },
  {
    type: '其他插件',
    content: [
      {
        name: 'gzip',
        code: 'gzip',
        imgUrl: require('@/assets/pluginImgs/icon_gzip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_gzip@2x.png'),
        message: '请求压缩',
        bigMessage: "gzip协议压缩就是依据HTTP协议进行压缩，不需要程序员进行压缩，解压编码，而是把压缩过程交给WEB服务器，将解压过程交给客户端。 如果客户端为支持GZIP压缩的浏览器，那么解压过程也不需要程序员参与，浏览器会按照一定的规则自动进行解压缩。",
        defaultShow: true,
        configuration: false
      },
      {
        name: 'proxy-cache',
        code: 'proxy_cache',
        imgUrl: require('@/assets/pluginImgs/icon_proxy@2x.png'),
        icon: require('@/assets/pluginIcon/icon_proxy@2x.png'),
        message: '代理缓存',
        bigMessage: "proxy-cache插件用于动态缓存请求的返回数据，可以将后端返回的应答缓存在API网关服务层面，有效降低后端的负荷，增加平滑度，开启插件后第一次请求会真实转发到服务端获取数据，之后在过期时间内的请求会从缓存中获取，默认使用redis分布式缓存。",
        defaultShow: true,
        configuration: false
      },
      {
        name: 'real_ip',
        code: 'real_ip',
        imgUrl: require('@/assets/pluginImgs/icon_real-ip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_real-ip@2x.png'),
        message: '获取真实ip',
        bigMessage: "real-ip插件用于获取客户端获取真实ip，默认情况下经过多层代理或转发后，服务端会获取多个不同ip，开启后网关层会自动解析判断获取客户端真实ip并通过响应头返回",
        defaultShow: true,
        configuration: false
      },
      {
        name: 'response-rewrite',
        code: 'response_rewrite',
        imgUrl: require('@/assets/pluginImgs/icon_response-rewrite@2x.png'),
        icon: require('@/assets/pluginIcon/icon_response-rewrite@2x.png'),
        message: '请求返回信息重写',
        bigMessage: "发布的服务，当接口还不可用时，开启返回值重写可以拦截掉对服务的请求，并给请求返回一个固定值的响应",
        defaultShow: true,
        configuration: false
      }
    ]
  }
]
export default plugs
