import {Tag} from './tag';

export class Certificate {
  id: number;
  name: string;
  description: string;
  date: string;
  price: number;
  creationDate: string;
  modificationDate: string;
  duration: number;
  tags: Array<Tag>;
}
