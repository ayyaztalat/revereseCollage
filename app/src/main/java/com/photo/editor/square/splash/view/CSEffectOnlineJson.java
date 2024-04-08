package com.photo.editor.square.splash.view;

import java.util.List;

/* loaded from: classes2.dex */
public class CSEffectOnlineJson {
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

    /* loaded from: classes2.dex */
    public static class DataBean {
        private List<ConfBean> conf;
        private String desc;
        private String icon;

        /* renamed from: id */
        private int f1630id;
        private String name;
        private int sort_num;

        public int getId() {
            return this.f1630id;
        }

        public void setId(int i) {
            this.f1630id = i;
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

        public int getSort_num() {
            return this.sort_num;
        }

        public void setSort_num(int i) {
            this.sort_num = i;
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

        /* loaded from: classes2.dex */
        public static class ConfBean {
            private String country_code;
            private int g_id;
            private int is_hot;
            private int is_lock;
            private int is_new;
            private int is_paid;
            private int is_rec;
            private MaterialBean material;
            private String max_version;
            private String min_version;
            private int position;
            private int sort_num;
            private int statue;
            private String uniqid;

            public String getUniqid() {
                return this.uniqid;
            }

            public void setUniqid(String str) {
                this.uniqid = str;
            }

            public int getStatue() {
                return this.statue;
            }

            public void setStatue(int i) {
                this.statue = i;
            }

            public String getCountry_code() {
                return this.country_code;
            }

            public void setCountry_code(String str) {
                this.country_code = str;
            }

            public int getPosition() {
                return this.position;
            }

            public void setPosition(int i) {
                this.position = i;
            }

            public int getIs_lock() {
                return this.is_lock;
            }

            public void setIs_lock(int i) {
                this.is_lock = i;
            }

            public int getIs_hot() {
                return this.is_hot;
            }

            public void setIs_hot(int i) {
                this.is_hot = i;
            }

            public int getIs_new() {
                return this.is_new;
            }

            public void setIs_new(int i) {
                this.is_new = i;
            }

            public int getIs_rec() {
                return this.is_rec;
            }

            public void setIs_rec(int i) {
                this.is_rec = i;
            }

            public int getIs_paid() {
                return this.is_paid;
            }

            public void setIs_paid(int i) {
                this.is_paid = i;
            }

            public int getSort_num() {
                return this.sort_num;
            }

            public void setSort_num(int i) {
                this.sort_num = i;
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

            public int getG_id() {
                return this.g_id;
            }

            public void setG_id(int i) {
                this.g_id = i;
            }

            public MaterialBean getMaterial() {
                return this.material;
            }

            public void setMaterial(MaterialBean materialBean) {
                this.material = materialBean;
            }

            /* loaded from: classes2.dex */
            public static class MaterialBean {
                private int data_number;
                private int data_size;
                private String data_zip;
                private String desc;
                private int g_id;
                private String icon;

                /* renamed from: id */
                private int f1631id;
                private String image;
                private String name;
                private String type;

                public int getId() {
                    return this.f1631id;
                }

                public void setId(int i) {
                    this.f1631id = i;
                }

                public int getG_id() {
                    return this.g_id;
                }

                public void setG_id(int i) {
                    this.g_id = i;
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

                public String getData_zip() {
                    return this.data_zip;
                }

                public void setData_zip(String str) {
                    this.data_zip = str;
                }

                public int getData_size() {
                    return this.data_size;
                }

                public void setData_size(int i) {
                    this.data_size = i;
                }

                public int getData_number() {
                    return this.data_number;
                }

                public void setData_number(int i) {
                    this.data_number = i;
                }

                public String getDesc() {
                    return this.desc;
                }

                public void setDesc(String str) {
                    this.desc = str;
                }

                public String getType() {
                    return this.type;
                }

                public void setType(String str) {
                    this.type = str;
                }
            }
        }
    }
}
