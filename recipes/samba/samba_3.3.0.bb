require samba.inc
require samba-basic.inc
LICENSE = "GPLv3"

# 3.3.0 and newer will upgrade your tdb database to a
# new version that you can not downgrade to use with older
# releases. More testing will happen before this is removed
# should there be further issues. Appears to work though :)
DEFAULT_PREFERENCE = "-1"

SRC_URI += "file://configure-3.3.0.patch \
            file://config-h.patch \
            file://mtab.patch \
            file://tdbheaderfix.patch "

PR = "r3"

EXTRA_OECONF += "\
	SMB_BUILD_CC_NEGATIVE_ENUM_VALUES=yes \
	samba_cv_CC_NEGATIVE_ENUM_VALUES=yes \
	linux_getgrouplist_ok=no \
	samba_cv_HAVE_BROKEN_GETGROUPS=no \
	samba_cv_HAVE_FTRUNCATE_EXTEND=yes \
	samba_cv_have_setresuid=yes \
	samba_cv_have_setresgid=yes \
	samba_cv_HAVE_WRFILE_KEYTAB=yes \
	"

do_configure() {
	oe_runconf
}

do_compile () {
	base_do_compile
}

SRC_URI[src.md5sum] = "adb048dc3988055533e1ea5d91d81f99"
SRC_URI[src.sha256sum] = "b00ca360e9b414744eff33ca4567f3eb1a28d32914e20f00b6672fbc141c9beb"
