
const token = "token";
const menuList = "menuList";
const userId = "userId";
const userName = "userName";
// 设置数据
export function setItem(key) {
    window.localStorage.setItem(key);
}
// 获取数据
export function getItem(key) {
    return window.localStorage.getItem(key);
}
// 删除数据
export function remove(key) {
    return window.localStorage.removeItem(key);
}
// 设置token
export function setToken(value) {
    window.localStorage.setItem(token, value);
}
// 获取token
export function getToken() {
    return window.localStorage.getItem(token);
}
// 移除token
export function removeToken() {
    return window.localStorage.removeItem(token);
}
// 设置菜单列表
export function setList(value) {
    window.localStorage.setItem(menuList, value);
}
// 获取菜单列表
export function getList() {
    return window.localStorage.getItem(menuList);
}
// 设置用户id
export function setUserId(value) {
    window.localStorage.setItem(userId, value);
}
// 获取用户id
export function getUserId() {
    return window.localStorage.getItem(userId);
}

// 设置用户名
export function setUserName(value) {
    window.localStorage.setItem(userName, value);
}
// 修改用户名
export function getUserName() {
    return window.localStorage.getItem(userName);
}