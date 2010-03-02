# Making a rootfs out of apks is not something anyone ever wants to do
#  - this file is here simply to quiet the rootfs_$pkg include

rootfs_apk_do_rootfs() {
	set +e
	mkdir -p ${IMAGE_ROOTFS}/system/app
	if [ ! -z "${PACKAGE_INSTALL}" ]; then
	   for i in ${PACKAGE_INSTALL}; do
	       cp $i ${IMAGE_ROOTFS}/system/app/
	   done
	fi

	${ROOTFS_POSTPROCESS_COMMAND}
}
