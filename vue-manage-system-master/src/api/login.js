import request from '../utils/request';

export function  adminLogin(data) {
    return request({
        url: '/admin/check_admin_password',
        method: 'post',
        data: data
    });
}

export function  doctorLogin(data) {
    return request({
        url: '/doc/check_doctor_password',
        method: 'post',
        data: data
    });
}

export function  patientLogin(data) {
    return request({
        url: '/pat/check_patient_password',
        method: 'post',
        data: data
    });
}
