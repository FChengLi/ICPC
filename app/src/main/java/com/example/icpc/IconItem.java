package com.example.icpc;

public class IconItem {
    private int iconResId;
    private String iconName;
    private String forumId; // 论坛 ID

    public IconItem(int iconResId, String iconName, String forumId) {
        this.iconResId = iconResId;
        this.iconName = iconName;
        this.forumId = forumId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getIconName() {
        return iconName;
    }

    public String getForumId() {
        return forumId;
    }
}
