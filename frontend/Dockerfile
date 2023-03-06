FROM node:latest AS build
WORKDIR /app
COPY . .

# RUN mkdir -p node_modules/node-sass/vendor/linux-x64-51
# RUN curl -L https://github.com/sass/node-sass/releases/download/v4.5.0/linux-x64-51_binding.node -o node_modules/node-sass/vendor/linux-x64-51/binding.node

RUN npm install
# ENV PATH /app/node_modules/.bin/:$PATH
RUN npm run build --prod

EXPOSE 80
FROM nginx:1.21.0-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/dist/tms-angular-frontend /usr/share/nginx/html