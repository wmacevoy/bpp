export RSYNC_RSH=ssh
if [ ! -r bpp.jar ] ; then
  echo "I don't see a bpp.jar file in this directory..."
  exit 1
fi
rsync -Pzr . wmacevoy@shell.sf.net:/home/groups/b/bp/bpp
