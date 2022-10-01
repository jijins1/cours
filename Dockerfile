FROM node:18.10.0-alpine3.15 as build
RUN npm install -g  @marp-team/marp-cli@latest

COPY ./polytech/design-system.md .
COPY ./polytech/security.md .
COPY ./polytech/observability.md .

RUN marp design-system.md
RUN marp security.md
RUN marp observability.md


FROM nginx:1.21.6

COPY polytech/assets /usr/share/nginx/html/assets/
COPY index.html /usr/share/nginx/html/index.html
COPY --from=build design-system.html /usr/share/nginx/html/design-system.html
COPY --from=build security.html /usr/share/nginx/html/security.html
COPY --from=build observability.html /usr/share/nginx/html/observability.html

