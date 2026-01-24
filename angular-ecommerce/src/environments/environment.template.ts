export const environment = {
  production: false, // Set to true for production
  auth0: {
    domain: 'YOUR_AUTH0_DOMAIN.auth0.com', // e.g., 'dev-qib13ufm5lh3ijvq.us.auth0.com'
    clientId: 'YOUR_CLIENT_ID', // e.g., 'cN1vtVOKTvrcdLQyPcjiqiKZuerQ5CHb'
    authorizationParams: {
      redirect_uri: 'YOUR_REDIRECT_URI', // e.g., 'http://localhost:4200/login/callback'
      audience: 'YOUR_API_AUDIENCE', // e.g., 'https://localhost:8080/api'
    },
    errorPath: '/error',
    // Enable refresh tokens for better token management
    useRefreshTokens: true,
    // Store tokens in localStorage for persistence across sessions
    cacheLocation: 'localstorage' as const
  }
};