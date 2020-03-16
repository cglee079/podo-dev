import getUser from '../middleware/get-user';

export default (context) => {
    console.log('plugin - get user ');
    getUser(context);
};
