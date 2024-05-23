export interface Client {
  name?: string;
  age?: number;
  email?: string;
  numAccount?: string;
  valueTransactional? : number;
}

export interface Account {
  id?: string,
  numAccount?: string,
  balance?: number
}

export interface Extract {
  id?: string,
  type?: string,
  value?: number,
  account?: Account,
  createdAt?: string
}