/**
 * Interface representing a resposne from the backend.
 */
export interface BackendResponse<T> {
    headers: any,
    statusCode: string,
    statusCodeValue: number,
    body: T,
}