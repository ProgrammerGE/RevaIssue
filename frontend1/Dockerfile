FROM node:22 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:alpine
# Force a clean directory
RUN rm -rf /usr/share/nginx/html/*

# COPY the contents, not just the folder
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Copy the config
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80