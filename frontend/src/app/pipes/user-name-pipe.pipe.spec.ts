import {UserNamePipe} from './user-name-pipe.pipe';

describe('UserNamePipePipe', () => {
  it('create an instance', () => {
    const pipe = new UserNamePipe();
    expect(pipe).toBeTruthy();
  });
});
