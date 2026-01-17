export const environment = {
  production: true,
  auth0: {
    domain: 'YOUR_PROD_DOMAIN.auth0.com',
    clientId: 'YOUR_PROD_CLIENT_ID',
    authorizationParams: {
      redirect_uri: 'https://yourdomain.com/login/callback',
      audience: 'https://YOUR_PROD_DOMAIN.auth0.com/api/v2/',
    },
    errorPath: '/error',
  }
};