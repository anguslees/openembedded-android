DESCRIPTION = "Android SDK"
SECTION = "libs"

DEPENDS += "sed-native"
RDEPENDS += "android-tools-sdk"
# Make end user provide this themselves
# RDEPENDS += virtual/java-native
inherit java
inherit sdk

INHIBIT_DEFAULT_DEPS = "1"

PV = 2.2_r02
SRC_URI = "http://dl.google.com/android/repository/android-${PV}-${BUILD_OS}.zip;name=sdk"
SRC_URI[sdk.md5sum] = "d11d4b3be48648ff68db05a75f26155d"
SRC_URI[sdk.sha256sum] = "682142096e6df1d626dce05b37df561e536067f465a870041cad770c219649ff"

S = "${WORKDIR}/android-${PV}-${BUILD_OS}"

# disk images are big, so ship them as a separate package
PACKAGES =+ "android-emulator-data"
FILES_android-emulator-data += "${datadir}/emulator"

do_compile() {
	:
}

bins = "dexdump aapt aidl"
java = "dx"

do_install() {
	install -d ${D}${bindir}
	for p in ${bins}; do
		install -m 0755 "${S}"/tools/$p ${D}${bindir}
	done

	for p in ${java}; do
		sed -e 's,^libdir=.*,libdir=${datadir}/java,' "${S}"/tools/$p >${D}${bindir}/$p
		chmod +x ${D}${bindir}/$p
	done

	install -d ${D}${datadir}/emulator
	cp -R -pf "${S}"/skins ${D}${datadir}/emulator
	cp -R -pf "${S}"/images ${D}${datadir}/emulator
	chmod a+rX -R ${D}${datadir}/emulator

	oe_jarinstall ${S}/android.jar
	oe_jarinstall ${S}/tools/lib/dx.jar
}
