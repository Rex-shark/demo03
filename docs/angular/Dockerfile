# Stage 1: 安裝相依套件
FROM node:22-slim AS deps
WORKDIR /app
COPY package*.json ./
RUN npm ci

# Stage 2: 編譯建置前端
FROM node:22-slim AS builder
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .

RUN npm run build

# 第二階段：使用 Nginx 映像來部署靜態檔案
FROM nginx:1.28-alpine-slim
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist/demo-angular3/browser /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
