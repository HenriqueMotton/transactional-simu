import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { environment } from "../../environments/environment";

export async function requestApi(
  method: string,
  route: string,
  body?: any,
  type?: string,
  pages?: any,
): Promise<AxiosResponse> {
  const instance = axios.create({
    baseURL: environment.apiUrl,
    timeout: 30000,
  });

  const urlRoute = buildUrlRoute(route, type, pages);
  
  const config: AxiosRequestConfig = {
    method,
    url: urlRoute,
    data: body,
  };;

  try {
    const response = await instance(config);
    return response;
  } catch (error: any) {
    if (error.response) {
      if (error.response.data && error.response.data.message) {
        throw error.response.data;
      }
      throw error.response;
    } else {
      throw error;
    }
  }
};

function buildUrlRoute(route: string, type?: string, pages?: any) {
  if (type === 'page') {
    const newPages = {
      size: pages?.size || 10,
      page: pages?.page || 0,
    };
    return `${route}pagina=${newPages.page}&totalPorPagina=${newPages.size}`;
  }
  return route;
}