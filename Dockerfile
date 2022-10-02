FROM node:18.10.0-alpine3.15 as build
RUN apk add --no-cache  chromium --repository=http://dl-cdn.alpinelinux.org/alpine/v3.10/main

RUN npm install -g  @marp-team/marp-cli@latest

COPY ./polytech/design-system.md .
COPY ./polytech/security.md .
COPY ./polytech/observability.md .
COPY ./polytech/disclaimer.md .

RUN marp design-system.md --pdf
RUN marp design-system.md
RUN marp security.md --pdf
RUN marp security.md
RUN marp observability.md --pdf
RUN marp observability.md
RUN marp disclaimer.md --pdf
RUN marp disclaimer.md


FROM nginx:1.21.6

COPY polytech/assets /usr/share/nginx/html/assets/
COPY index.html /usr/share/nginx/html/index.html
COPY --from=build design-system.* /usr/share/nginx/html/
COPY --from=build security.* /usr/share/nginx/html/
COPY --from=build observability.* /usr/share/nginx/html/
COPY --from=build disclaimer.* /usr/share/nginx/html/

