declare module '@apiverve/moonphases' {
  export interface moonphasesOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface moonphasesResponse {
    status: string;
    error: string | null;
    data: MoonPhasesData;
    code?: number;
  }


  interface MoonPhasesData {
      phase:           string;
      phaseEmoji:      string;
      waxing:          boolean;
      waning:          boolean;
      lunarAge:        number;
      lunarAgePercent: number;
      lunationNumber:  number;
      lunarDistance:   number;
      nextFullMoon:    Date;
      lastFullMoon:    Date;
  }

  export default class moonphasesWrapper {
    constructor(options: moonphasesOptions);

    execute(callback: (error: any, data: moonphasesResponse | null) => void): Promise<moonphasesResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: moonphasesResponse | null) => void): Promise<moonphasesResponse>;
    execute(query?: Record<string, any>): Promise<moonphasesResponse>;
  }
}
