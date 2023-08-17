import styles from './SearchBar.module.scss'

type SearchBarProps = {
  handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void
}

export default function SearchBar({ handleSubmit }: SearchBarProps) {
  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <input
        name='text'
        type='text'
        className={styles.input}
        placeholder='여행하고 싶은 지역을 입력하세요'
        required
        autoComplete='off'
      />
      <button type='submit' className={styles.searchBtn}>
        검색
      </button>
    </form>
  )
}
