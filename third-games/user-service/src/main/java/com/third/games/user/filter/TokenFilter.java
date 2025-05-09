package com.third.games.user.filter;

//@Component
//public class TokenFilter extends OncePerRequestFilter {
//    @Autowired
//    private UserContext userContext;
//
//    @Autowired
//    private TokenService tokenService;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    )
//            throws IOException, ServletException {
//
//        HandlerMethod handlerMethod = (HandlerMethod) request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
//        if (handlerMethod != null) {
//            if (handlerMethod.getMethod().isAnnotationPresent(NoAuth.class)
//                    || handlerMethod.getBeanType().isAnnotationPresent(NoAuth.class)) {
//                filterChain.doFilter(request, response);
//                return;
//            }
//        }
//
//        // 从 Header 获取 Token
//        String token = request.getHeader("Authorization");
//        if (StringUtils.hasText(token)) {
//            try {
//                LoginUser loginUser = tokenService.parseToken(token);
//                userContext.set(loginUser); // 保存到线程上下文
//            } catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.getWriter().write("Invalid token");
//                return;
//            }
//        } else {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Missing token");
//            return;
//        }
//
//        try {
//            filterChain.doFilter(request, response);
//        } finally {
//            userContext.clear();
//        }
//    }
//}

