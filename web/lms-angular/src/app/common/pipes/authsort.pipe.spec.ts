import { AuthsortPipe } from './authsort.pipe';

describe('AuthsortPipe', () => {
  let pipe: AuthsortPipe;

  beforeEach(()=>{
    pipe = new AuthsortPipe();
  })
  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should return 1', () => {
    const author1 = {
      authorName: "Alpha"
    };
    const author2 = {
      authorName: "Beta"
    };
    expect(pipe.transform([author1,author2])).toEqual([author1,author2]);
  });

  it('should return 0', () => {
    const author1 = {
      authorName: "A"
    };
    const author2 = {
      authorName: "B"
    };
    expect(pipe.transform([author2,author1])).toEqual([author1,author2]);
  });

  it('should return 1', () => {
    const author1 = {
      authorName: "A"
    };
    const author2 = {
      authorName: "A"
    };
    expect(pipe.transform([author1,author2])).toEqual([author1,author2]);
  });

});
