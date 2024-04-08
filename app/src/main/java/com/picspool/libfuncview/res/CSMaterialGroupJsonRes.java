package com.picspool.libfuncview.res;

import java.util.List;

/* loaded from: classes.dex */
public class CSMaterialGroupJsonRes {
    private List<DataBean> data;
    private String msg;
    private int server_time;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setServer_time(int i) {
        this.server_time = i;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    /* loaded from: classes.dex */
    public static class DataBean {
        private List<ConfBean> conf;
        private String desc;
        private String icon;

        /* renamed from: id */
        private int f1665id;
        private String name;
        private String sort_num;

        public int getId() {
            return this.f1665id;
        }

        public void setId(int i) {
            this.f1665id = i;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String str) {
            this.icon = str;
        }

        public String getSort_num() {
            return this.sort_num;
        }

        public void setSort_num(String str) {
            this.sort_num = str;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public List<ConfBean> getConf() {
            return this.conf;
        }

        public void setConf(List<ConfBean> list) {
            this.conf = list;
        }

        /* loaded from: classes.dex */
        public static class ConfBean {
            private String g_id;
            private String is_h_banner;
            private String is_h_cell;
            private String is_hot;
            private String is_lock;
            private String is_m_banner;
            private String is_new;
            private String is_paid;
            private String is_rec;
            private MaterialBean material;
            private String max_version;
            private String min_version;
            private String position;
            private String sort_num;
            private String uniqid;
            private String update_time;

            public String getUniqid() {
                return this.uniqid;
            }

            public void setUniqid(String str) {
                this.uniqid = str;
            }

            public String getPosition() {
                return this.position;
            }

            public void setPosition(String str) {
                this.position = str;
            }

            public String getIs_lock() {
                return this.is_lock;
            }

            public void setIs_lock(String str) {
                this.is_lock = str;
            }

            public String getIs_hot() {
                return this.is_hot;
            }

            public void setIs_hot(String str) {
                this.is_hot = str;
            }

            public String getIs_new() {
                return this.is_new;
            }

            public void setIs_new(String str) {
                this.is_new = str;
            }

            public String getIs_rec() {
                return this.is_rec;
            }

            public void setIs_rec(String str) {
                this.is_rec = str;
            }

            public String getIs_m_banner() {
                return this.is_m_banner;
            }

            public void setIs_m_banner(String str) {
                this.is_m_banner = str;
            }

            public String getIs_h_banner() {
                return this.is_h_banner;
            }

            public void setIs_h_banner(String str) {
                this.is_h_banner = str;
            }

            public String getIs_h_cell() {
                return this.is_h_cell;
            }

            public void setIs_h_cell(String str) {
                this.is_h_cell = str;
            }

            public String getIs_paid() {
                return this.is_paid;
            }

            public void setIs_paid(String str) {
                this.is_paid = str;
            }

            public String getSort_num() {
                return this.sort_num;
            }

            public void setSort_num(String str) {
                this.sort_num = str;
            }

            public String getMin_version() {
                return this.min_version;
            }

            public void setMin_version(String str) {
                this.min_version = str;
            }

            public String getMax_version() {
                return this.max_version;
            }

            public void setMax_version(String str) {
                this.max_version = str;
            }

            public String getUpdate_time() {
                return this.update_time;
            }

            public void setUpdate_time(String str) {
                this.update_time = str;
            }

            public String getG_id() {
                return this.g_id;
            }

            public void setG_id(String str) {
                this.g_id = str;
            }

            public MaterialBean getMaterial() {
                return this.material;
            }

            public void setMaterial(MaterialBean materialBean) {
                this.material = materialBean;
            }

            /* loaded from: classes.dex */
            public static class MaterialBean {
                private String banner;
                private String data_number;
                private String data_size;
                private String data_zip;
                private String desc;
                private String effect_zip;
                private String icon;

                /* renamed from: id */
                private int f1666id;
                private String image;
                private String name;

                public int getId() {
                    return this.f1666id;
                }

                public void setId(int i) {
                    this.f1666id = i;
                }

                public String getName() {
                    return this.name;
                }

                public void setName(String str) {
                    this.name = str;
                }

                public String getIcon() {
                    return this.icon;
                }

                public void setIcon(String str) {
                    this.icon = str;
                }

                public String getImage() {
                    return this.image;
                }

                public void setImage(String str) {
                    this.image = str;
                }

                public String getBanner() {
                    return this.banner;
                }

                public void setBanner(String str) {
                    this.banner = str;
                }

                public String getEffect_zip() {
                    return this.effect_zip;
                }

                public void setEffect_zip(String str) {
                    this.effect_zip = str;
                }

                public String getData_number() {
                    return this.data_number;
                }

                public void setData_number(String str) {
                    this.data_number = str;
                }

                public String getData_zip() {
                    return this.data_zip;
                }

                public void setData_zip(String str) {
                    this.data_zip = str;
                }

                public String getData_size() {
                    return this.data_size;
                }

                public void setData_size(String str) {
                    this.data_size = str;
                }

                public String getDesc() {
                    return this.desc;
                }

                public void setDesc(String str) {
                    this.desc = str;
                }
            }
        }
    }
}
