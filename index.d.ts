declare module '@apiverve/moonphases' {
  export interface moonphasesOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface moonphasesResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class moonphasesWrapper {
    constructor(options: moonphasesOptions);

    execute(callback: (error: any, data: moonphasesResponse | null) => void): Promise<moonphasesResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: moonphasesResponse | null) => void): Promise<moonphasesResponse>;
    execute(query?: Record<string, any>): Promise<moonphasesResponse>;
  }
}
