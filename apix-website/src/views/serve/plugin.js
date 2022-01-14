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
        defaultShow: true,
        configuration: true
      },
      {
        name: "basic_auth",
        code: "base_auth",
        imgUrl: require('@/assets/pluginImgs/icon_basic-auth@2x.png'),
        icon: require('@/assets/pluginIcon/icon_basic-auth@2x.png'),
        message: "为您的服务添加基本身份验证",
        defaultShow: true,
        configuration: false
      },
      {
        name: "OAuth2.0",
        code: "oauth2",
        imgUrl: require('@/assets/pluginImgs/icon_oauth2.0@2x.png'),
        icon: require('@/assets/pluginIcon/icon_oauth2.0@2x.png'),
        message: "将SDX与第三方OAuth2.0授权服务器集成",
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
        defaultShow: true,
        configuration: true

      },
      {
        name: "IP 白名单控制",
        code: "white_list_ip",
        imgUrl: require('@/assets/pluginImgs/icon_ip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_ipblack@2x.png'),
        message: "可以发出请求的白名单",
        defaultShow: true,
        configuration: true
      },
      {
        name: "cors跨域",
        code: "cors",
        imgUrl: require('@/assets/pluginImgs/icon_cors@2x.png'),
        icon: require('@/assets/pluginIcon/icon_cors@2x.png'),
        message: "允许开发人员从浏览器发出请求",
        defaultShow: true,
        configuration: true
      },
      {
        name: "防篡改签名",
        code: "sign",
        imgUrl: require('@/assets/pluginImgs/icon_sign@2x.png'),
        icon: require('@/assets/pluginIcon/icon_sign@2x.png'),
        message: "-",
        defaultShow: true,
        configuration: true

      },
      {
        name: '防网络重放攻击',
        code: 'replay_attacks',
        imgUrl: require('@/assets/pluginImgs/icon_attcks@2x.png'),
        icon: require('@/assets/pluginIcon/icon_replay_attacks@2x.png'),
        message: '网络请求重试限制',
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
        defaultShow: true,
        configuration: false
      },
      {
        name: 'http log',
        code: 'http_log',
        imgUrl: require('@/assets/pluginImgs/icon_http_log @2x.png'),
        icon: require('@/assets/pluginIcon/icon_http_log @2x.png'),
        message: '将请求和响应日志发送到http服务器',
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
        defaultShow: true,
        configuration: false
      },
      {
        name: 'proxy-cache',
        code: 'proxy_cache',
        imgUrl: require('@/assets/pluginImgs/icon_proxy@2x.png'),
        icon: require('@/assets/pluginIcon/icon_proxy@2x.png'),
        message: '代理缓存',
        defaultShow: true,
        configuration: false
      },
      {
        name: 'real_ip',
        code: 'real_ip',
        imgUrl: require('@/assets/pluginImgs/icon_real-ip@2x.png'),
        icon: require('@/assets/pluginIcon/icon_real-ip@2x.png'),
        message: '获取真实ip',
        defaultShow: true,
        configuration: false
      },
      {
        name: 'response-rewrite',
        code: 'response_rewrite',
        imgUrl: require('@/assets/pluginImgs/icon_response-rewrite@2x.png'),
        icon: require('@/assets/pluginIcon/icon_response-rewrite@2x.png'),
        message: '请求返回信息重写',
        defaultShow: true,
        configuration: false
      }
    ]
  }
]
export default plugs
