# Stage 1 - Build React App inside temporary Node container
FROM node:14 as builder
WORKDIR /usr/src/app
COPY . .
RUN npm install
RUN npm run-script build

#Run Stage Start
FROM nginx
COPY default.conf /etc/nginx/conf.d/default.conf
#Copy production build files from builder phase to nginx
COPY --from=builder /usr/src/app/build /usr/share/nginx/html
