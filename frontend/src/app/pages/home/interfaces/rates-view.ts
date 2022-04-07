export interface RatesView {
  minRate: number;
  maxRate: number;
  avgRate: number;
  rates: RatesMap[];
}
export interface RatesMap {
  name: string;
  value: number;
}
