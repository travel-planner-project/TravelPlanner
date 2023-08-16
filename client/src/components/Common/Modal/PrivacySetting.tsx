import styles from './PrivacySetting.module.scss'

function PrivacySetting() {
  return (
    <fieldset className={styles.privacySetting}>
      <label htmlFor='public'>
        <input
          id='public'
          value='public'
          name='privacySetting'
          className={styles.radio}
          type='radio'
          defaultChecked
        />
        전체공개
      </label>
      <label htmlFor='private'>
        <input
          id='private'
          value='private'
          name='privacySetting'
          className={styles.radio}
          type='radio'
        />
        나만 보기
      </label>
    </fieldset>
  )
}

export default PrivacySetting
